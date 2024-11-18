const regex = /(g|i+)+t/
console.time('t1:')
'giiiiiiiiiiiiiiiiiiiiiiv'.search(regex);
console.timeEnd('t1:')

console.time('t2:')
'giiiiiiiiiiiiiiiiiiiiiit'.search(regex);
console.timeEnd('t2:')