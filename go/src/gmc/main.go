/*
The web frontend for GroupMyContacts
*/
package main

import (
	"net/http"
	"flag"
)

var addr = flag.String("addr", ":80", "address to listen on")
var root = flag.String("root", ".", "directory to have the templates")

func main() {
	flag.Parse()
	http.Handle("/", http.FileServer(http.Dir(*root + "/static")))
	panic(http.ListenAndServe(*addr, nil)) 
}

