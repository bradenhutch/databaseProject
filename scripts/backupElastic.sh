#!/usr/bin/env bash

createBackup="curl -XPUT 'http://localhost:9200/_snapshot/my_backup' -H 'Content-Type: application/json' -d '{\"type\": \"fs\",\"settings\": {\"location\": \"/var/log/elasticsearch\",\"compress\": true}}'"

eval $createBackup