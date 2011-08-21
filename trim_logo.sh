#!/bin/bash

for file in $1
do
	if [[ -f $file ]]
	then	
		convert "$file" -trim +repage "trim/$file";
		echo "$file";
	fi
done
