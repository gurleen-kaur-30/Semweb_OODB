#!/bin/sh
PROJECT_NAME=$1
CLASSES_DIR=../protegeCodeGen/$PROJECT_NAME/impl

for c in $CLASSES_DIR/*
do
    echo "Updating $c file "
    sed -i "s/public class/@Entity\n public class/g" "$c"
    l="`grep 'public class' "$c"`"
    l_1="`echo "$l" | rev | cut -c 2- | rev`"
    l_2="${l_1}, Serializable {"
    id_var="${l_2}\n\t private static final long serialVersionUID = 1L;\n\t @Id @GeneratedValue\n\t private long id;"
    name_var="${id_var}\n\t private String name;"

    func="`grep "get.*{$" "$c"`" 
    # echo "$func"
    func="${func//(/;}"
    func_trim="`echo "$func" | rev | cut -c 4- | rev `"
    # echo "$func_trim"
    res=""
    v="public"
    for s in $func_trim;

    do 
        if [ "$s" = "$v" ];
        then
            res="${res}\n${s}"
        else
            res="${res} ${s}"
        fi;
    done

    new="${res//public/private}"
    new="${new//get/""}"
    # echo "${new}"
    name_var="${name_var}\n\t${new}"
    sed -i "s/${l}/${name_var}/g" "$c"

    constructor="super(inference, iri);"
    name_con="\t name = iri.toString()"

    init_private_var="`grep -o "get.*{$" "$c"`" 
    init_private_var="`echo "$init_private_var" | rev | cut -c 2- | rev`"
    init_var_name="$init_private_var"
    init_var_name="${init_var_name//get/ }"
    init_var_name="${init_var_name//()/=}"

    final_init=""

    set $init_var_name
    for i in $init_private_var; do
        final_init="$final_init$1$i;\n\t" 
        shift
    done

    new_constructor="${constructor}\n\t${name_con}\n\t${final_init}"
    sed -i "s/${constructor}/${new_constructor}/g" "$c"

    # echo "$final_init"
done
