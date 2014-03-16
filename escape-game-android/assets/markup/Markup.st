EVAL Class addNewClass: (Object subclass: 'Markup' variables: 'contents' classVariables: '')

COMMENT Markup
I am the abstract base class for markup language generators.

I define the protocol that my subclasses must implement.
!

METHOD Markup
initialize
    super initialize.
    contents := ''
!

META Markup
fileExtension
    self error: 'Subclass responsibility'.
    ^ '.txt'
!

METHOD Markup
fileExtension
    ^ self class fileExtension
!

META Markup
linkExtension
    self error: 'Subclass responsibility'.
    ^ '.txt'
!

METHOD Markup
linkExtension
    ^ self class linkExtension
!

METHOD Markup
begin
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
end
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
cr
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
tab
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
heading: aString level: aNumber
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
link: aString to: aURL
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
code: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
blockQuote: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
italic: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
bold: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
underline: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
bar
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
image: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
list: aStringOrBlock
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
orderedList: aStringOrBlock
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
orderedList: aStringOrBlock start: aNumber
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!

METHOD Markup
item: aString
    self error: 'Subclass responsibility'.
    ^ 'ERROR'
!