#!/usr/bin/env bash

reviewId=$1

deleteReview="curl -X DELETE 'http://localhost:9200/reviews/_doc/$1?pretty'"
eval $deleteReview