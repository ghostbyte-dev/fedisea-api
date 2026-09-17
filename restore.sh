#!/bin/bash
set -a
source /root/fedisea-api/.env
set +a

# Set your backup file names here (or pass as args)
DB_BACKUP_FILE="$1"
ICONS_BACKUP_FILE="$2"

if [[ -z "$DB_BACKUP_FILE" || -z "$ICONS_BACKUP_FILE" ]]; then
  echo "Usage: $0 <db_backup.sql.gz> <icons_backup.tar.gz>"
  exit 1
fi

echo "Starting restore..."

# Copy Postgres backup into container
echo "Copying Postgres backup $DB_BACKUP_FILE into container..."
docker cp "$DB_BACKUP_FILE" fedisea_db:/backup.sql.gz

# Restore database inside container
echo "Restoring Postgres database from $DB_BACKUP_FILE..."
docker exec -e PGPASSWORD="$DB_PASSWORD" fedisea_db sh -c "gunzip < /backup.sql.gz | psql -U $DB_USER $DB_NAME"

# Remove backup file from container
docker exec fedisea_db rm /backup.sql.gz

# Restore the fedisea_icons named volume via a throwaway container
echo "Restoring icons volume from $ICONS_BACKUP_FILE..."
docker run --rm \
  -v fedisea-api_fedisea_icons:/icons \
  -v "$(pwd)":/backup \
  alpine sh -c "rm -rf /icons/* && tar -xzf /backup/$(basename "$ICONS_BACKUP_FILE") -C /icons"

echo "Restore completed."