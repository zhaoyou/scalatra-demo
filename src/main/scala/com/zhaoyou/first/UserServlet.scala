package com.zhaoyou.first

import org.scalatra._
import scalate.ScalateSupport
import com.zhaoyou.first.data._


class UserServlet extends ScalatraServlet with ScalateSupport {
  get("/:id") {
    contentType = "text/html"
    println(params("id") + "xxxxxxxxxxxx")
    scaml("user", ("name" -> DataManager.getName(params("id"))))
  }
}


