////////////////////////////////////////////////////////////
// Hello World example
// http://bergel.eu/athena/scripts/
//
// Author: Alexandre Bergel (alexandre@bergel.eu)
////////////////////////////////////////////////////////////
EVAL Class addNewClass: (Object subclass: 'FooClass' variables: '' classVariables: '')
METHOD FooClass
foo
	^ 10
!
METHOD FooClass
bar
	^ self foo * 2
!
METHOD FooClass
helloWorld
	^ self hello + self world
!
METHOD FooClass
hello
	^ 'hello '
!
METHOD FooClass
world
	^ 'world'
!
EVAL <117 (FooClass new helloWorld)>
