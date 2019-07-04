set -o errexit

if [ "$#" -ne 1 ]; then
    echo Missing version parameter
    echo Usage: build-image.sh \<version\>
    exit 1
fi

VERSION=$1
SCRIPTDIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )

pushd "$SCRIPTDIR/seata-business-service"
	./mvnw clean package -DskipTests=true
	docker build --pull -t "registry.sloth.com/ipaas/business-service:${VERSION}" .
popd

pushd "$SCRIPTDIR/seata-order-service"
	./mvnw clean package -DskipTests=true
	docker build --pull -t "registry.sloth.com/ipaas/order-service:${VERSION}" .
popd

pushd "$SCRIPTDIR/seata-storage-service"
	./mvnw clean package -DskipTests=true
	docker build --pull -t "registry.sloth.com/ipaas/storage-service:${VERSION}" .
popd

pushd "$SCRIPTDIR/seata-user-service"
	./mvnw clean package -DskipTests=true
	docker build --pull -t "registry.sloth.com/ipaas/user-service:${VERSION}" .
popd
