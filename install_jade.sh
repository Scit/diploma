#!/bin/sh

mvn install:install-file -Dfile=jade.jar -DgroupId=jade \
        -DartifactId=jade -Dversion=4.3.2 -Dpackaging=jar
