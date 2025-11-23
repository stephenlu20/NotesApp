#!/bin/bash

DB_FILE="notes.db"
DATA_FILE="dump.sql"

# If the database file exists, you can remove it to start fresh
if [ -f "$DB_FILE" ]; then
    echo "Removing existing database file $DB_FILE..."
    rm "$DB_FILE"
fi

# Create a new SQLite database and import the data
echo "Creating database and importing data from $DATA_FILE..."
sqlite3 "$DB_FILE" < "$DATA_FILE"

echo "Database $DB_FILE created successfully."
