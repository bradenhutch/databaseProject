#return all reviews
curl -X GET 'http://localhost:9200/reviews/_search?pretty' -H 'Content-Type: application/json' -d '{"query":{"match_all":{}}}'

#delete review by ID
curl -X DELETE 'http://localhost:9200/reviews/_doc/""INSERT ID HERE"""?pretty'
