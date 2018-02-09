#!/usr/bin/env bash

ENVIRONMENT=$1
HOCKEYAPP_TOKEN=$2
RELEASE_NOTES=$3

if [[ ${ENVIRONMENT} == 'release' ]]
then
    APK_DIR='app/build/outputs/apk/release/app-release.apk'
elif [[ ${ENVIRONMENT} == 'debug' ]]
then
    APK_DIR='app/build/outputs/apk/debug/app-debug.apk'
else
    echo -e "Exit Code 2:\nIncorrect Environment supplied"
    exit 2;
fi

if [[ ${HOCKEYAPP_TOKEN} == '' ]]
then
    echo -e "Exit Code 2:\nNo Hockeyapp Token supplied"
    exit 2;
fi

echo ${RELEASE_NOTES}

curl -F "status=2" -F "notify=1" -F "notes=$RELEASE_NOTES" \
 -F "notes_type=0" -F "ipa=@$APK_DIR" -H \
 "X-HockeyAppToken: $HOCKEYAPP_TOKEN" https://rink.hockeyapp.net/api/2/apps/upload