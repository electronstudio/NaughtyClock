rm -R /tmp/naughtyclock
cp -R dist /tmp/naughtyclock
rm /tmp/naughtyclock/NaughtyClock.exe
cp README.TXT /tmp/naughtyclock
DIR=$PWD
cd /tmp
tar -cf naughtyclock.tar naughtyclock
bzip2 naughtyclock.tar
mv naughtyclock.tar.bz2  "$DIR"/Output
