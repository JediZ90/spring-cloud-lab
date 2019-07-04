set -o errexit

if [ "$#" -ne 1 ]; then
    echo Missing version parameter
    echo Usage: build-image.sh \<version\>
    exit 1
fi

VERSION=$1

./mvnw clean package -DskipTests=true

#plain build -- no ratings
docker build --pull -t "registry.sloth.com/ipaas/examples-bookinfo-reviews-v1:${VERSION}" --build-arg service_version=v1 .
