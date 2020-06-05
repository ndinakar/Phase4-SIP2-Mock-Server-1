
echo "Parameter = $1"
PROJ_NAME="Phase4-SIP2-Mock-Server"
PROJ_DIR="/opt/Phase4-SIP2-Mock-Server"
PROJ_DIR_TAG=""
VAR_SLASH="/"

PROJ_DIR_TAG=$PROJ_DIR$VAR_SLASH$PROJ_NAME;

# echo $PROJ_DIR_TAG
mkdir $PROJ_NAME
echo $PROJ_DIR
chmod 777 /$PROJ_DIR

cd $PROJ_DIR

git clone https://github.com/ResearchCollectionsAndPreservation/Phase4-SIP2-Mock-Server.git

ls  $PROJ_DIR_TAG/
# echo $PROJ_DIR_TAG
cd $PROJ_DIR_TAG

if [ ! -z $1 ] ;then
    git checkout tags/$1
fi
# ls -l
pwd

# echo Change permissions
chmod -R 750 $PROJ_DIR_TAG \
    && chmod 750 $PROJ_DIR_TAG/maven


./mvnw clean install -U -DskipTests=true
#./mvnw clean installl package -T 10


ln -s $PROJ_DIR_TAG/target/phase4-scsb-mock-sip-server-2.9.8.jar /etc/init.d/phase4-scsb-mock-sip-server
cp $PROJ_DIR_TAG/target/phase4-scsb-mock-sip-server-2.9.8.jar /opt

