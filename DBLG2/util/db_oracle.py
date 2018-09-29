
import cx_Oracle
import sys
from random import randint, uniform

CONN_INFO = {
    'host': '127.0.0.1',
    'port': 1521,
    'user': 'system',
    'pass': 'manager',
	'service':'Quiz_1'
}

CONN_STR = '{user}/{pass}@{host}:{port}/{service}'.format(**CONN_INFO)

class DB:
    def __init__(self):
        self.conn = cx_Oracle.connect(CONN_STR, mode = cx_Oracle.SYSDBA)

    def query_dml(self, query, params={}):
        cursor = self.conn.cursor()
        rows = cursor.execute(query, params).fetchall()
        cols = tuple([d[0] for d in cursor.description])
        result = []
        for row in rows:
            dic = dict(zip(cols, row))
            result.append(dic)
        cursor.close()
        self.conn.commit()
        return result

    def query_ddl(self, query, params={}):
        cursor = self.conn.cursor()
        try:
            cursor.execute(query, params)
            print("everything ok")
        except cx_Oracle.Error as err:
            print(err)
            return -1
        return 0

    def insert(self, query):
        cursor = self.conn.cursor()
        cursor.execute(query)
        self.conn.commit()

def test_oracle():

    QUERY_DML = '''
                select * 
                    from t1
                '''
				
    INSERT = '''insert into t2 values(5,6,0.5,'python')'''

    QUERY_DDL = '''
                create table personas(
                    id int
                    nombre varchar(30)
                )
                '''

    db = DB()
    for i in range(100000):
        print(str(i)+") "+INSERT)
        db.insert(INSERT)
    #result = db.query_dml(QUERY_DML)
    #print(result)
    #for i,e in enumerate(result):
    #   print(i,e)

def main(argv):
    db = DB()
    INSERT ='''INSERT INTO %s values(1,2,3.4,'python')''' % argv[1]
    for i in range(int(argv[2])):
        #INSERT='''INSERT INTO %s VALUES(%d,%d,%.2f,'%s')'''%(argv[1],randint(1,100),randint(1,100),uniform(0.1,100.100),"python"+str(i))
        #print(INSERT)       
        db.insert(INSERT)


main(sys.argv)