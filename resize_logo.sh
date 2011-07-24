#!/bin/bash

we=$1
he=$2
t=$4

for file in $3
do
	if [[ -f $file ]]
	then	
		echo -n "${file}: "
		w=$(identify -format "%w" "${file}");
		h=$(identify -format "%h" "${file}");
		echo "${w}×${h}"
		
		filename=$(basename $file);
		if [[ "$w" -eq "$1" && "$h" -eq "$2" ]]
		then
			echo "Size matched";
			# если размеры совпадают
			cp "$file" "$t/$filename";
		else
		# если по ширине или высоте не совпадает
			if [[ $w -gt $we || $h -gt $he ]]
			# если по ширине или высоте больше эталона, подгоняем в размер
			then
				if [[ `echo "scale=4;(($we/$he)>($w/$h))" | bc` -eq "1" ]]
				then
				# подгон по высоте
					convert "$file" -resize "x$he" "$t/$filename~";
				else
				# подгон по ширине
					convert "$file" -resize "$we" "$t/$filename~";
				fi
			fi
			# расширяем полотно до эталонного размера
			convert "$t/$filename~" -gravity center -background None -extent "${we}x${he}" "$t/$filename";
			echo "Converted";
		fi
	fi

done
