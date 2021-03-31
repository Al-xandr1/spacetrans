#!/usr/bin/env bash

rep_dirs=$(ls -d Report*/)
current=$(pwd)
for i in ${rep_dirs[*]} ; do
    echo "$i"
    cd "$i" && zip "../${i%/}.zip" -r ./* && cd "$current" || exit 1
done

zip Reports.zip ./*.zip
