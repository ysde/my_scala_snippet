## Code Snippet
```scala
val pattern = "(https?://)?([^/?&]+)/?(.*)".r

def getDomain(url: String) = url match {
  case pattern(_, domain, _) => "domain:" + domain
  case _ => "not matched"
}
```

## Test Result:

getDomain("https://www.aaa.com/") => www.aaa.com

getDomain("http://www.aaa.com/") => www.aaa.com

getDomain("https://www.aaa.com/?ddd=1") => www.aaa.com

getDomain("https://www.aaa.com?ddd=1") => www.aaa.com

getDomain("https://www.aaa.com?ddd=1&bbb=2") => www.aaa.com

getDomain("www.aaa.com?ddd=1&bbb=2") => www.aaa.com