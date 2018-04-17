#!/usr/bin/env bash

returnReviews="curl -X GET 'http://localhost:9200/reviews/_search?pretty' -H 'Content-Type: application/json' -d '{\"query\":{\"match_all\":{}}}'"

eval $returnReviews