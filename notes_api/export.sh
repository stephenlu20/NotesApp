#!/bin/bash
sqlite3 notes.db ".schema" > schema.sql
sqlite3 notes.db ".dump" > dump.sql