
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

    def query_dml(self, query):
        cursor = self.conn.cursor()
        rows = cursor.execute(query).fetchall()
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
            result = cursor.execute(query, params)
            print("everything ok")
            print(result)
        except cx_Oracle.Error as err:
            print(err)
            return -1
        self.conn.commit()
        return 0

    def insert(self, query):
        cursor = self.conn.cursor()
        try:
            cursor.execute(query)
            self.conn.commit()
        except cx_Oracle.Error as err:
            print(err)












def test():
    db = DB()
    db.query_ddl('''create table ciclistas(nombre varchar(20))''')
    db.insert('''insert into ciclistas values('Amador')''')
    result = db.query_dml("select * from ciclistas")

    print(result)
    for i,e in enumerate(result):
       print(i,e)



def main(argv):
    print("inicio del programa")
    dblink = argv[1]
    user = argv[2]
    password = argv[3]
    hostname = argv[4]
    port = argv[5]
    service = argv[6]

    dblink_info = {
            'dblink': dblink,
            'user': user,
            'password': password,
            'hostname': hostname,
            'port':port,
            'service':service
    }       

    dblink_str = '''
        CREATE PUBLIC DATABASE LINK {dblink}
                CONNECT TO {user} IDENTIFIED BY {password}
                USING '(description = 
                        (address = 
                        (protocol = tcp)
                        (host = {hostname})
                        (Port = {port}) )
                        (connect_data = 
                                (SERVICE_NAME = {service} ) )
                        )'
        '''.format(**dblink_info)

    print(dblink_str)
    db = DB()
    db.query_ddl(dblink_str)

    print("fin del programa")


main(sys.argv)