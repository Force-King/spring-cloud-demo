#!/usr/bin/env bash

CURRENT_DIR=$(dirname $0)

[ -d ${CURRENT_DIR}/core.tar.gz ] && rm ${CURRENT_DIR}/demo-coree.tar.gz
cp ${CURRENT_DIR}/../demo-coree.jar demo-coree.jar
tar -zcvf ${CURRENT_DIR}/demo-coree.tar.gz ${CURRENT_DIR}/run.conf ${CURRENT_DIR}/run.sh ${CURRENT_DIR}/static.conf ${CURRENT_DIR}/demo-coree.jar
rm -rf ${CURRENT_DIR}/demo-coree.jar
