/*
 * File created on Dec 23, 2013 
 *
 * Copyright (c) 2013 Carl Harris, Jr.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

grammar Expression;

statement:
    expression? EOF;

expression: exclusiveOrOp (OP_OR exclusiveOrOp)?;

exclusiveOrOp: andOp (OP_XOR andOp)?;
    
andOp: equalityOp (OP_AND equalityOp)?;
  
equalityOp: relationalOp (equalityOperator relationalOp)?;

relationalOp:
    additiveOp (relationalOperator additiveOp)?
 |  isTypeOp
 ;

isTypeOp: additiveOp (isOperator TYPE)?;
 
additiveOp: multiplicativeOp (additiveOperator multiplicativeOp)*;

multiplicativeOp: unaryOp (multiplicativeOperator unaryOp)*;

unaryOp: unaryOperator? function;

function:
   FN_IF LPAREN (expression COMMA expression)+ (COMMA expression)? RPAREN
 | LPAREN expression RPAREN
 | operand
 ;

operand: REF | NUMBER | DATE | TIME | BOOLEAN | STRING;

equalityOperator: OP_EQ | OP_NEQ; 

relationalOperator: OP_LT | OP_LEQ | OP_GT | OP_GEQ | OP_MATCH;

isOperator: OP_IS | OP_IS_NOT;

additiveOperator: OP_PLUS | OP_MINUS;

multiplicativeOperator: OP_MULT | OP_DIV;

unaryOperator: OP_PLUS | OP_MINUS | OP_NOT;

TYPE: TYPE_NUMBER | TYPE_DATE | TYPE_BOOLEAN | TYPE_STRING | TYPE_BLANK;

TYPE_NUMBER: N U M B E R;

TYPE_DATE: D A T E;

TYPE_BOOLEAN: B O O L E A N;

TYPE_STRING: S T R I N G;

TYPE_BLANK: B L A N K | E M P T Y | N U L L;

BOOLEAN: T R U E | F A L S E;

DATE: [0-9][0-9][0-9][0-9]'-'[0-9][0-9]'-'[0-9][0-9];

TIME: [0-9][0-9]':'[0-9][0-9]':'[0-9][0-9];
      
NUMBER: ('0' | [1-9][0-9]*) ('.' [0-9]* ([eE] [+-]? [0-9]+)?)?;

STRING: '\'' .*? '\'';

ZEROES: '0'+;

FN_IF: I F;

OP_MINUS: '-';

OP_PLUS: '+';

OP_MATCH: '~=';

OP_EQ: '==' | '=';

OP_NEQ: '!=';

OP_NOT: '!';

OP_MULT: '*';

OP_DIV: '/';

OP_LEQ: '<=';

OP_LT: '<';

OP_GEQ: '>=';

OP_GT:  '>';

OP_IS_NOT:  I S  WS  N O T;

OP_IS:  I S;

OP_AND: '&&';

OP_OR: '||';

OP_XOR: '^';

LPAREN: '(';

RPAREN: ')';

COMMA: ',';

REF: ('\'' [A-Za-z0-9 ]+ '\'' '!')? '$'? [A-Za-z]+ '$'? [0-9]+;

WS: [ \t\n\r]+ -> skip;

fragment A: 'A' | 'a';
fragment B: 'B' | 'b';
fragment C: 'C' | 'c';
fragment D: 'D' | 'd';
fragment E: 'E' | 'e';
fragment F: 'F' | 'f';
fragment G: 'G' | 'g';
fragment H: 'H' | 'h';
fragment I: 'I' | 'i';
fragment J: 'J' | 'j';
fragment K: 'K' | 'k';
fragment L: 'L' | 'l';
fragment M: 'M' | 'm';
fragment N: 'N' | 'n';
fragment O: 'O' | 'o';
fragment P: 'P' | 'p';
fragment Q: 'Q' | 'q';
fragment R: 'R' | 'r';
fragment S: 'S' | 's';
fragment T: 'T' | 't';
fragment U: 'U' | 'u';
fragment V: 'V' | 'v';
fragment W: 'W' | 'w';
fragment X: 'X' | 'x';
fragment Y: 'Y' | 'y';
fragment Z: 'Z' | 'z';
