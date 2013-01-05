#!/bin/bash
mysql -u root mahout < dumptable.sql
cp /tmp/recom.csv ./data

