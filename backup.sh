#!/bin/bash
set -a
source /root/fedisea-api/.env
set +a

# Generate timestamp (format: YYYYMMDD_HHMMSS)
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")

PROJECT_DIR="/root/fedisea-api"
BACKUP_DIR="/root/fedisea-backup"
DB_BACKUP_FILE="backup_$TIMESTAMP.sql.gz"
ICONS_BACKUP_FILE="icons_backup_$TIMESTAMP.tar.gz"

mkdir -p "$BACKUP_DIR"

# Dump Postgres database
/usr/bin/docker exec -e PGPASSWORD="$DB_PASSWORD" fedisea_db sh -c "pg_dump -U $DB_USER $DB_NAME | gzip > /$DB_BACKUP_FILE"

/usr/bin/docker cp fedisea_db:/$DB_BACKUP_FILE "$BACKUP_DIR/"

/usr/bin/docker exec fedisea_db rm /$DB_BACKUP_FILE

# Backup the fedisea_icons named volume via a throwaway container
/usr/bin/docker run --rm \
  -v fedisea-api_fedisea_icons:/icons:ro \
  -v "$BACKUP_DIR":/backup \
  alpine sh -c "tar -czf /backup/$ICONS_BACKUP_FILE -C /icons ."

echo "Backup completed:"
echo "  - Postgres: $BACKUP_DIR/$DB_BACKUP_FILE"
echo "  - Icons volume: $BACKUP_DIR/$ICONS_BACKUP_FILE"

echo "Deleting old backups..."
find "$BACKUP_DIR" -type f -mtime +7 -delete

echo "Finished"