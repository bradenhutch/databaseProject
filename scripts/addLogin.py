from pymongo import MongoClient
import datetime
import sys

username = sys.argv[1]
now = datetime.datetime.now().strftime("%Y-%m-%d %H:%M")

client = MongoClient('localhost',27017)
db = client.logins.logins

template = {
		"userName": username, 
		"loginDate": now
		}

db.insert(template)