//This class handles blocking delay operations

EVAL Class addNewClass: (Object subclass: 'Delay' variables: 'millisecs' classVariables: '')


META Delay
forMilliseconds: aMillisecs
	| aMe |
	aMe := self new.
	aMe setMillisecs: aMillisecs.
	^aMe.
!



METHOD Delay
setMillisecs: aMillisecs
	millisecs := aMillisecs.
!


METHOD Delay
wait
	|startTime elapsedTime|
	startTime := <133>.
	elapsedTime := 0.

	[ elapsedTime < millisecs ] whileTrue: [ elapsedTime := <133> - startTime ].
!



