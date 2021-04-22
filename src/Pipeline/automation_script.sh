#!/bin/sh
PROJECT_NAME=$1
CLASSES_DIR=../ProtegeGenCode/$PROJECT_NAME/impl
echo "$CLASSES_DIR"
# CLASSES_DIR=../impl
for c in $CLASSES_DIR/*
do
    echo "Updating $c file "
    sed -i "s/public class/@Entity\n public class/g" "$c"
    l="`grep 'public class' "$c"`"
    l_1="`echo "$l" | rev | cut -c 2- | rev`"
    l_2="${l_1}, Serializable {"
    id_var="${l_2}\n\t private static final long serialVersionUID = 1L;\n\t @Id @GeneratedValue\n\t private long id;"
    # echo "$l"
    # echo "$l_2"
    echo "$id_var"
    sed -i "s/$l/$id_var/g" "$c"
done
