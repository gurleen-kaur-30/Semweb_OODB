s="Default"$1".java"
c=../ProtegeGenCode/CollegeProtege/impl/$s
name_var="`grep 'name;' "$c" | head -1`"
inverse_var="${name_var}\n\t@Embedded private Collection<? extends $2> $3;\n"
sed -i "s/${name_var}/${inverse_var}/g" "$c"

l1="public void set$3(Object p) { \n\t\t"
l2="Default$2 p_new = (Default$2)p;\n\t\t"
l3="HashSet<Default$2> p1  = new HashSet<Default$2>();\n\t\t"
l4="p1.add((Default$2) p_new);\n\t\t"
l5="this.$3 = p1;\n}"
func=${l1}${l2}${l3}${l4}${l5}

cons_init="`grep 'name = iri.toString();' "$c"`"
cons_new="${cons_init}\n\t\tTaughtBy  = new HashSet<Default$2>();"
sed -i "0,/${cons_init}/{s/${cons_init}/${cons_new}/;}" "$c"


cons_end="`grep '}' "$c" | head -1`"
func="}\n\t${func}"
sed -i "0,/${cons_end}/{s/${cons_end}/${func}/;}" "$c"



