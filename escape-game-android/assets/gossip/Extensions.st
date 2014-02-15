// extensions to existing classes, to be integrated later
METHOD Dictionary
allKeys
    ^ (allEntries collect: [ :e | e at: 1 ]) asArray
!
METHOD Dictionary
allValues
    ^ (allEntries collect: [ :e | e at: 2 ]) asArray
!
