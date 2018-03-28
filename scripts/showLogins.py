from pymongo import MongoClient

client = MongoClient('localhost',27017)
db = client.logins.logins

cursor = db.find()

for document in cursor:
	print(document)