//This class handles blocking delay operations

EVAL Class addNewClass: (Object subclass: 'Delay' variables: 'millisecs' classVariables: '')


META Delay
forMilliseconds: aNumber
	| aMe |
	aMe := self new.
	aMe setMillisecs: aNumber.
	^aMe.
!



METHOD Delay
setMillisecs: aNumber
	millisecs := aNumber.
!


METHOD Delay
wait
	|startTime elapsedTime|
	startTime := <133>.
	elapsedTime := 0.

	[ elapsedTime < millisecs ] whileTrue: [ elapsedTime := <133> - startTime ].
!



