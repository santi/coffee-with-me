#!/usr/bin/env sh

# Exit on failures
set -e;

echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
BACKEND_IMAGE_NAME=santiario/coffee-backend
FRONTEND_IMAGE_NAME=santiario/coffee-frontend
VERSION=$(git rev-parse HEAD)

# Deploy backend
./gradlew coffee-backend:build

docker build --pull -t $BACKEND_IMAGE_NAME:"$VERSION" -t $BACKEND_IMAGE_NAME:latest ./coffee-backend
docker push $BACKEND_IMAGE_NAME:$VERSION
docker push $BACKEND_IMAGE_NAME:latest
echo "PUBLISHED DOCKER IMAGE $BACKEND_IMAGE_NAME:$VERSION"

# Deploy frontend
docker build --pull -t $FRONTEND_IMAGE_NAME:"$VERSION" -t $FRONTEND_IMAGE_NAME:latest ./coffee-frontend
docker push $FRONTEND_IMAGE_NAME:$VERSION
docker push $FRONTEND_IMAGE_NAME:latest
echo "PUBLISHED DOCKER IMAGE $FRONTEND_IMAGE_NAME:$VERSION"
