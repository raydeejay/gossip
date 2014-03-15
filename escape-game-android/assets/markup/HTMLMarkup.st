EVAL Class addNewClass: (Markup subclass: 'HTMLMarkup' variables: '' classVariables: '')

COMMENT HTMLMarkup
I am an HTML generator.
!

METHOD HTMLMarkup
initialize
    super initialize.
    self begin
!

METHOD HTMLMarkup
render
    self end.
    ^ contents
!

METHOD HTMLMarkup
extension
    ^ '.html'
!

METHOD HTMLMarkup
begin
    ^ '<html><body>'
!

METHOD HTMLMarkup
end
    ^ '<body></html>'
!

METHOD HTMLMarkup
newline
    ^ '<br>' , Char newline asString
!

METHOD HTMLMarkup
tab
    ^ '&#09;'
!

METHOD HTMLMarkup
heading: aString level: aNumber
    | level |

    level := aNumber asString.
    ^ '<h' , level, '>' , aString , '</h' , level , '>'
!

METHOD HTMLMarkup
link: aString to: aURL
    ^ '<a href="' , aURL , '">' , aString , '</a>'
!

METHOD HTMLMarkup
code: aString
    ^ '<pre>' , aString , '</pre>'
!

METHOD HTMLMarkup
blockquote: aString
    ^ '<blockquote>' , aString , '</blockquote>'
!

METHOD HTMLMarkup
italic: aString
    ^ '<i>' , aString , '</i>'
!

METHOD HTMLMarkup
bold: aString
    ^ '<b>' , aString , '</b>'
!

METHOD HTMLMarkup
underline: aString
    ^ '<u>' , aString , '</u>'
!

METHOD HTMLMarkup
bar
    ^ '<hr/>'
!

METHOD HTMLMarkup
image: aURL
    ^ '<img src="' , aURL , '" />'
!

METHOD HTMLMarkup
list: aStringOrBlock
    ^ '<ul>' , aStringOrBlock , '</ul>'
!

METHOD HTMLMarkup
orderedList: aStringOrBlock
    ^ '<ol>' , aStringOrBlock , '</ol>'
!

METHOD HTMLMarkup
orderedList: aStringOrBlock start: aNumber
    ^ '<ol start="', aNumber printString , '">' , aStringOrBlock , '</ol>'
!

METHOD HTMLMarkup
item: aString
    ^ '<li>' , aString , '</li>'
!

