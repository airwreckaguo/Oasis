from sklearn.neighbors import NearestNeighbors
from sklearn.metrics import mean_squared_error
import numpy as np
import metric_learn
from sklearn.preprocessing import StandardScaler
import pickle
from flask import Flask, url_for, request, g, jsonify
import sqlite3
from sqlite3 import Error
import random, string

conn = None 

def randomword(length):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(length))

def create_connection(db_file):
    """ create a database connection to a SQLite database """
    try:
        conn = sqlite3.connect(db_file)
        print(sqlite3.version)
        return conn
    except Error as e:
        print(e)
        return conn

def get_db(db_file):
    db = getattr(g, '_database', None)
    if db is None:
        db = g._database = create_connection(db_file)
    return db


app = Flask(__name__)

@app.teardown_appcontext
def teardown_db(exception):
    db = getattr(g, '_database', None)
    if db is not None:
        db.close()
        
@app.route('/')
def api_root():
    return 'Welcome'

@app.route('/getmatch')
def api_get_match():
    name = request.form.get('name')
    age = request.form.get('age')
    gender = request.form.get('gender')
    relationship = request.form.get('relationship')
    cause_of_death = request.form.get('cause of death')
    # In the future, in the foreground we are also going to add a order, a four-digit numbers that orders
    # the which of these are most important to the user in finding a match.
    # Ex) 1234. Each digit represents how important age, gender, relationship, and cause of death on a scale of 1-4.
    # 1 being the least important to 4 being the most important. 
    order = request.form.get('order')
    #convert variables age...and so on to numbers
    # first we're going to use dummy numbers to make sure this program works, since the frontend is not connected directly
    # Now to generate a random string of length 10
    name = randomword(6)
    age = random.randint(10, 100)
    gender = random.randint(0, 1)
    relationship = random.randint(1, 4)
    cause_of_death = random.randint(1, 11)
    order = ''.join(random.choice([str(1), str(2), str(3), str(4)]) for i in range(4))
    print(order)
  #  raw = [age, gender, relationship, cause_of_death]
 #   normed = [i/sum(raw) for i in raw]
    # scales all variables after normalizing them, with respect to how important the user finds them.
 #   age = int(number[0])*raw[0];
 #   gender = int(number[1])*raw[1];
 #   relationship = int(number[2])*raw[2];
 #   cause_of_death = int(number[3])*raw[3];
    
    conn = get_db("pythonsqlite1.db")
    cur = conn.cursor()
    # selects only the data we will use
    cur.execute('SELECT name, age, gender, relationship, cause from users')
    alist = cur.fetchall()
    # converts alist to a numpy array
    data1 = np.array(alist)                                                                                                                                                                                                                                                                                                                     
    data = (name, age, gender, relationship, cause_of_death)
    # if there's less than 4 entries, we can't execute the ML algorithm. simply insert them in to the table.
    if data1.shape[0] < 4:
        cur.execute("INSERT INTO users (name, age, gender, relationship, cause) VALUES (?, ?, ?, ?, ?)", data)
        conn.commit()
        return jsonify('')
    
    # exclude first row and first column
    # is a unicode string, we convert it to a string
    X = data1[0:,1:].astype(int)
    print("Here is X\n")
    print(X)

    # np.allclose(data. data1)
    nbrs = NearestNeighbors(n_neighbors = 4, algorithm='ball_tree').fit(X)
 
    distances, indices = nbrs.kneighbors(X)

    #Input query to get the index of the nearest neighbor (the stranger that best matches their profile)
    index = nbrs.kneighbors([[age, gender, relationship, cause_of_death]])[1][0][0];
    match_name = alist[index][0];
    print(match_name + "\n")

    # use machine learning algorithm to find the name of the best match
    # search in alist for the same values to get the name
    # add the query's name of the SQL database

    cur.execute("INSERT INTO users (name, age, gender, relationship, cause) VALUES (?, ?, ?, ?, ?)", data)
    conn.commit()
    return jsonify(match_name)


def main():
    global conn
    database = "pythonsqlite1.db"
    sql_create_users_table = """CREATE TABLE IF NOT EXISTS users (
                                    id integer PRIMARY KEY,
                                    name text NOT NULL,
                                    age integer NOT NULL,
                                    gender integer NOT NULL,
                                    relationship integer NOT NULL,
                                    cause integer NOT NULL
                                );"""
    conn = create_connection(database)
    # If there is a connection
    if conn is not None:
        cur = conn.cursor()
        quer = "SELECT name FROM sqlite_master WHERE type='table' AND name='table_name';"
        cur.execute(quer)
        table_list = cur.fetchall()
        print(table_list)
        # if there already is a table, we don't need to create one
        if len(table_list)!= 0:
            return
        # there aren't any tables existing, so create a users table
        cur.execute(sql_create_users_table)
        # data = [('john', 20, 1, 3, 6), ('asdfsd', 20, 3, 5, 9), ('friendo', 3, 92, 2, 10), ('hi', 3, 5, 79, 2), ('HALLO', 4, 19, 1, 2), ('future', 1, 30, 2, 1), ('hallo', 10, 9, 29, 2)]
        #cur.executemany("INSERT INTO users (name, age, gender, relationship, cause) VALUES (?, ?, ?, ?, ?)", data)
        #conn.commit()



if __name__ == '__main__':
    main()
    app.run(debug=True)
