(function (e) {
    function t(t) {
        for (var s, i, o = t[0], u = t[1], c = t[2], b = 0, d = []; b < o.length; b++) i = o[b], Object.prototype.hasOwnProperty.call(a, i) && a[i] && d.push(a[i][0]), a[i] = 0;
        for (s in u) Object.prototype.hasOwnProperty.call(u, s) && (e[s] = u[s]);
        l && l(t);
        while (d.length) d.shift()();
        return r.push.apply(r, c || []), n()
    }

    function n() {
        for (var e, t = 0; t < r.length; t++) {
            for (var n = r[t], s = !0, o = 1; o < n.length; o++) {
                var u = n[o];
                0 !== a[u] && (s = !1)
            }
            s && (r.splice(t--, 1), e = i(i.s = n[0]))
        }
        return e
    }

    var s = {}, a = {app: 0}, r = [];

    function i(t) {
        if (s[t]) return s[t].exports;
        var n = s[t] = {i: t, l: !1, exports: {}};
        return e[t].call(n.exports, n, n.exports, i), n.l = !0, n.exports
    }

    i.m = e, i.c = s, i.d = function (e, t, n) {
        i.o(e, t) || Object.defineProperty(e, t, {enumerable: !0, get: n})
    }, i.r = function (e) {
        "undefined" !== typeof Symbol && Symbol.toStringTag && Object.defineProperty(e, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(e, "__esModule", {value: !0})
    }, i.t = function (e, t) {
        if (1 & t && (e = i(e)), 8 & t) return e;
        if (4 & t && "object" === typeof e && e && e.__esModule) return e;
        var n = Object.create(null);
        if (i.r(n), Object.defineProperty(n, "default", {
            enumerable: !0,
            value: e
        }), 2 & t && "string" != typeof e) for (var s in e) i.d(n, s, function (t) {
            return e[t]
        }.bind(null, s));
        return n
    }, i.n = function (e) {
        var t = e && e.__esModule ? function () {
            return e["default"]
        } : function () {
            return e
        };
        return i.d(t, "a", t), t
    }, i.o = function (e, t) {
        return Object.prototype.hasOwnProperty.call(e, t)
    }, i.p = "/ofd/";
    var o = window["webpackJsonp"] = window["webpackJsonp"] || [], u = o.push.bind(o);
    o.push = t, o = o.slice();
    for (var c = 0; c < o.length; c++) t(o[c]);
    var l = u;
    r.push([0, "chunk-vendors"]), n()
})({
    0: function (e, t, n) {
        e.exports = n("56d7")
    }, "034f": function (e, t, n) {
        "use strict";
        n("85ec")
    }, 2661: function (e, t, n) {
        "use strict";
        n("2d25")
    }, "2d25": function (e, t, n) {
    }, 3662: function (e, t, n) {
        "use strict";
        n.d(t, "a", (function () {
            return r
        }));
        n("96cf");
        var s = n("1da1");
        Array.prototype.pipeline = function () {
            var e = Object(s["a"])(regeneratorRuntime.mark((function e(t) {
                var n, s, a;
                return regeneratorRuntime.wrap((function (e) {
                    while (1) switch (e.prev = e.next) {
                        case 0:
                            if (null !== this && "undefined" !== typeof this) {
                                e.next = 2;
                                break
                            }
                            throw new TypeError("Array.prototype.pipeline called on null or undefined");
                        case 2:
                            if ("function" === typeof t) {
                                e.next = 4;
                                break
                            }
                            throw new TypeError(t + " is not a function");
                        case 4:
                            a = this.length >>> 0, n = 0;
                        case 6:
                            if (!(a > n)) {
                                e.next = 13;
                                break
                            }
                            return e.next = 9, t(s, this[n], n, this);
                        case 9:
                            s = e.sent;
                        case 10:
                            ++n, e.next = 6;
                            break;
                        case 13:
                            return e.abrupt("return", s);
                        case 14:
                        case"end":
                            return e.stop()
                    }
                }), e, this)
            })));
            return function (t) {
                return e.apply(this, arguments)
            }
        }();
        var a = function () {
            for (var e = this, t = arguments.length, n = new Array(t), s = 0; s < t; s++) n[s] = arguments[s];
            return n.pipeline((function (t, n) {
                return n.call(e, t)
            }))
        }, r = a
    }, "56d7": function (e, t, n) {
        "use strict";
        n.r(t);
        n("e260"), n("e6cf"), n("cca6"), n("a79d");
        var s = n("2b0e"), a = function () {
                var e = this, t = e.$createElement, n = e._self._c || t;
                return n("div", {attrs: {id: "app"}}, [n("HelloWorld")], 1)
            }, r = [], i = function () {
                var e = this, t = e.$createElement, n = e._self._c || t;
                return n("el-container", {
                    staticStyle: {
                        width: "100vw",
                        height: "100vh"
                    }
                }, [n("el-header", {
                    staticStyle: {
                        background: "#F5F5F5",
                        display: "flex",
                        height: "40px",
                        border: "1px solid #e8e8e8",
                        "align-items": "center"
                    }
                }, [n("div", {
                    staticClass: "upload-icon",
                    on: {click: e.downloadFile}
                }, [n("div", {staticClass: "upload-icon"}, [e._v("下载文件")]), n("font-awesome-icon", {attrs: {icon: "download"}})], 1), n("div", {
                    staticClass: "upload-icon",
                    on: {click: e.doPrint}
                }, [n("div", {staticClass: "upload-icon"}, [e._v("打印")]), n("font-awesome-icon", {attrs: {icon: "print"}})], 1), e.ofdObj ? n("div", {
                    staticStyle: {
                        display: "flex",
                        "align-items": "center"
                    }
                }, [n("div", {
                    staticClass: "scale-icon",
                    staticStyle: {"margin-left": "10px"},
                    on: {click: e.plus}
                }, [n("font-awesome-icon", {attrs: {icon: "search-plus"}})], 1), n("div", {
                    staticClass: "scale-icon",
                    on: {click: e.minus}
                }, [n("font-awesome-icon", {attrs: {icon: "search-minus"}})], 1), n("div", {staticClass: "scale-icon"}, [n("font-awesome-icon", {
                    attrs: {icon: "step-backward"},
                    on: {click: e.firstPage}
                })], 1), n("div", {
                    staticClass: "scale-icon",
                    staticStyle: {"font-size": "18px"},
                    on: {click: e.prePage}
                }, [n("font-awesome-icon", {attrs: {icon: "caret-left"}})], 1), n("div", {staticClass: "scale-icon"}, [e._v(" " + e._s(e.pageIndex) + "/" + e._s(e.pageCount) + " ")]), n("div", {
                    staticClass: "scale-icon",
                    staticStyle: {"font-size": "18px"},
                    on: {click: e.nextPage}
                }, [n("font-awesome-icon", {attrs: {icon: "caret-right"}})], 1), n("div", {
                    staticClass: "scale-icon",
                    on: {click: e.lastPage}
                }, [n("font-awesome-icon", {attrs: {icon: "step-forward"}})], 1)]) : e._e()]), n("el-main", {
                    directives: [{
                        name: "loading",
                        rawName: "v-loading",
                        value: e.loading,
                        expression: "loading"
                    }], staticStyle: {height: "auto", background: "#808080", padding: "0"}
                }, [n("div", {
                    staticClass: "left-section",
                    staticStyle: {display: "none"},
                    attrs: {id: "leftMenu"}
                }, [n("div", {
                    staticClass: "text-icon", on: {
                        click: function (t) {
                            return e.demo(1)
                        }
                    }
                }, [n("p", [e._v("电子发票")])]), n("div", {
                    staticClass: "text-icon", on: {
                        click: function (t) {
                            return e.demo(2)
                        }
                    }
                }, [n("p", [e._v("电子公文")])]), n("div", {
                    staticClass: "text-icon", on: {
                        click: function (t) {
                            return e.demo(3)
                        }
                    }
                }, [n("p", [e._v("骑缝章")])]), n("div", {
                    staticClass: "text-icon", on: {
                        click: function (t) {
                            return e.demo(4)
                        }
                    }
                }, [n("p", [e._v("多页文档")])])]), n("div", {
                    ref: "contentDiv",
                    staticClass: "main-section",
                    attrs: {id: "content"},
                    on: {mousewheel: e.scrool}
                })]), n("div", {
                    ref: "sealInfoDiv",
                    staticClass: "SealContainer",
                    attrs: {id: "sealInfoDiv", hidden: "hidden"}
                }, [n("div", {
                    staticClass: "SealContainer mask",
                    on: {click: e.closeSealInfoDialog}
                }), n("div", {staticClass: "SealContainer-layout"}, [n("div", {staticClass: "SealContainer-content"}, [n("p", {staticClass: "content-title"}, [e._v("签章信息")]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("签章人")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSigner"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("签章提供者")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spProvider"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("原文摘要值")]), n("span", {
                    staticClass: "value",
                    staticStyle: {cursor: "pointer"},
                    attrs: {id: "spHashedValue"},
                    on: {
                        click: function (t) {
                            return e.showMore("原文摘要值", "spHashedValue")
                        }
                    }
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("签名值")]), n("span", {
                    staticClass: "value",
                    staticStyle: {cursor: "pointer"},
                    attrs: {id: "spSignedValue"},
                    on: {
                        click: function (t) {
                            return e.showMore("签名值", "spSignedValue")
                        }
                    }
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("签名算法")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSignMethod"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("版本号")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spVersion"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("验签结果")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "VerifyRet"}
                }, [e._v("[无效的签章结构]")])]), n("p", {staticClass: "content-title"}, [e._v("印章信息")]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("印章标识")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealID"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("印章名称")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealName"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("印章类型")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealType"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("有效时间")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealAuthTime"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("制章日期")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealMakeTime"}
                }, [e._v("[无效的签章结构]")])]), n("div", {staticClass: "subcontent"}, [n("span", {staticClass: "title"}, [e._v("印章版本")]), n("span", {
                    staticClass: "value",
                    attrs: {id: "spSealVersion"}
                }, [e._v("[无效的签章结构]")])])]), n("input", {
                    staticStyle: {position: "absolute", right: "1%", top: "1%"},
                    attrs: {type: "button", name: "", id: "", value: "X"},
                    on: {
                        click: function (t) {
                            return e.closeSealInfoDialog()
                        }
                    }
                })])]), n("el-dialog", {
                    attrs: {title: e.title, visible: e.dialogFormVisible},
                    on: {
                        "update:visible": function (t) {
                            e.dialogFormVisible = t
                        }
                    }
                }, [n("span", {staticStyle: {"text-align": "left"}}, [e._v(" " + e._s(e.value) + " ")]), n("div", {
                    staticClass: "dialog-footer",
                    attrs: {slot: "footer"},
                    slot: "footer"
                }, [n("el-button", {
                    attrs: {type: "primary"}, on: {
                        click: function (t) {
                            e.dialogFormVisible = !1
                        }
                    }
                }, [e._v("确 定")])], 1)])], 1)
            }, o = [], u = n("8374"), c = u["a"], l = (n("2661"), n("2877")),
            b = Object(l["a"])(c, i, o, !1, null, "1f2bedf5", null), d = b.exports,
            f = {name: "App", components: {HelloWorld: d}}, h = f,
            p = (n("034f"), Object(l["a"])(h, a, r, !1, null, null, null)), v = p.exports, m = (n("5717"), n("5c96")),
            g = n.n(m), y = (n("0fae"), n("ecee")), x = n("c074"), w = n("ad3d"), O = n("bc3a"), S = n.n(O),
            j = n("14b5");
        s["default"].prototype.$axios = S.a, y["c"].add(x["a"]), s["default"].config.productionTip = !1, s["default"].component("font-awesome-icon", w["a"]), s["default"].use(g.a), s["default"].use(j["a"]), new s["default"]({
            render: function (e) {
                return e(v)
            }
        }).$mount("#app")
    }, 5717: function (e, t, n) {
    }, "67d3": function (e, t, n) {
        "use strict";
        (function (e) {
            n.d(t, "c", (function () {
                return f
            })), n.d(t, "a", (function () {
                return h
            })), n.d(t, "b", (function () {
                return p
            }));
            n("99af"), n("4160"), n("c975"), n("baa5"), n("4ec9"), n("b64b"), n("d3b7"), n("ac1f"), n("25f0"), n("3ca3"), n("5319"), n("1276"), n("159b"), n("ddb0");
            var s = n("b85c"), a = n("3835"), r = (n("96cf"), n("1da1")), i = n("3662"), o = n("c4e3"), u = n.n(o),
                c = n("6b33"), l = n("73fd"), b = n("a9c6"), d = n("74db"), f = function (e) {
                    return new Promise((function (t, n) {
                        u.a.loadAsync(e).then((function (e) {
                            t(e)
                        }), (function (e) {
                            n(e)
                        }))
                    }))
                }, h = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, s, a;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.next = 2, T(t, "OFD.xml");
                                case 2:
                                    return n = e.sent, s = n["json"]["ofd:OFD"]["ofd:DocBody"], a = [], a = a.concat(s), e.abrupt("return", [t, a]);
                                case 7:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), p = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, r, i, o, u, c, l, b;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    n = Object(a["a"])(t, 2), r = n[0], i = n[1], o = [], u = Object(s["a"])(i), e.prev = 3, u.s();
                                case 5:
                                    if ((c = u.n()).done) {
                                        e.next = 29;
                                        break
                                    }
                                    if (l = c.value, !l) {
                                        e.next = 27;
                                        break
                                    }
                                    return e.next = 10, v(r, l);
                                case 10:
                                    return b = e.sent, e.next = 13, m(b);
                                case 13:
                                    return b = e.sent, e.next = 16, y(b);
                                case 16:
                                    return b = e.sent, e.next = 19, x(b);
                                case 19:
                                    return b = e.sent, e.next = 22, w(b);
                                case 22:
                                    return b = e.sent, e.next = 25, O(b);
                                case 25:
                                    b = e.sent, o.push(b);
                                case 27:
                                    e.next = 5;
                                    break;
                                case 29:
                                    e.next = 34;
                                    break;
                                case 31:
                                    e.prev = 31, e.t0 = e["catch"](3), u.e(e.t0);
                                case 34:
                                    return e.prev = 34, u.f(), e.finish(34);
                                case 37:
                                    return e.abrupt("return", o);
                                case 38:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[3, 31, 34, 37]])
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), v = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n) {
                        var a, r, i, o, u, l, b, d, f, h, p, v, m, g, y, x, w, O;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return a = n["ofd:DocRoot"], a = Object(c["m"])(a), r = a.split("/")[0], i = n["ofd:Signatures"], e.next = 6, _(t, i, r);
                                case 6:
                                    o = e.sent, u = {}, l = Object(s["a"])(o), e.prev = 9, l.s();
                                case 11:
                                    if ((b = l.n()).done) {
                                        e.next = 29;
                                        break
                                    }
                                    if (d = b.value, !(d.sealObj && Object.keys(d.sealObj).length > 0)) {
                                        e.next = 27;
                                        break
                                    }
                                    if ("ofd" !== d.sealObj.type) {
                                        e.next = 22;
                                        break
                                    }
                                    return e.next = 17, D(d);
                                case 17:
                                    f = e.sent, h = Object(s["a"])(f);
                                    try {
                                        for (h.s(); !(p = h.n()).done;) v = p.value, d.stampAnnot.boundary = Object(c["l"])(d.stampAnnot["@_Boundary"]), d.stampAnnot.pageRef = d.stampAnnot["@_PageRef"], u[d.stampAnnot["@_PageRef"]] || (u[d.stampAnnot["@_PageRef"]] = []), u[d.stampAnnot["@_PageRef"]].push({
                                            type: "ofd",
                                            obj: v,
                                            stamp: d
                                        })
                                    } catch (S) {
                                        h.e(S)
                                    } finally {
                                        h.f()
                                    }
                                    e.next = 27;
                                    break;
                                case 22:
                                    m = "data:image/" + d.sealObj.type + ";base64," + btoa(String.fromCharCode.apply(null, d.sealObj.ofdArray)), g = [], g = g.concat(d.stampAnnot), y = Object(s["a"])(g);
                                    try {
                                        for (y.s(); !(x = y.n()).done;) w = x.value, w && (O = {
                                            img: m,
                                            pageId: w["@_PageRef"],
                                            boundary: Object(c["l"])(w["@_Boundary"]),
                                            clip: Object(c["l"])(w["@_Clip"])
                                        }, u[w["@_PageRef"]] || (u[w["@_PageRef"]] = []), u[w["@_PageRef"]].push({
                                            type: d.sealObj.type,
                                            obj: O,
                                            stamp: d
                                        }))
                                    } catch (S) {
                                        y.e(S)
                                    } finally {
                                        y.f()
                                    }
                                case 27:
                                    e.next = 11;
                                    break;
                                case 29:
                                    e.next = 34;
                                    break;
                                case 31:
                                    e.prev = 31, e.t0 = e["catch"](9), l.e(e.t0);
                                case 34:
                                    return e.prev = 34, l.f(), e.finish(34);
                                case 37:
                                    return e.abrupt("return", [t, r, a, u]);
                                case 38:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[9, 31, 34, 37]])
                    })));
                    return function (t, n) {
                        return e.apply(this, arguments)
                    }
                }(), m = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, s, r, i, o, u, c, l, b, d, f;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return n = Object(a["a"])(t, 4), s = n[0], r = n[1], i = n[2], o = n[3], e.next = 3, T(s, i);
                                case 3:
                                    if (u = e.sent, c = u["json"]["ofd:Document"], l = c["ofd:Annotations"], b = [], !l) {
                                        e.next = 15;
                                        break
                                    }
                                    if (-1 !== l.indexOf("/") && (d = l.substring(0, l.indexOf("/"))), -1 === l.indexOf(r) && (l = "".concat(r, "/").concat(l)), !s.files[l]) {
                                        e.next = 15;
                                        break
                                    }
                                    return e.next = 13, T(s, l);
                                case 13:
                                    l = e.sent, b = b.concat(l["json"]["ofd:Annotations"]["ofd:Page"]);
                                case 15:
                                    return e.next = 17, g(d, b, r, s);
                                case 17:
                                    return f = e.sent, e.abrupt("return", [s, r, c, o, f]);
                                case 19:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), g = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n, a, r) {
                        var i, o, u, l, b, d, f, h, p, v, m, g, y, x, w;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    i = {}, o = Object(s["a"])(n), e.prev = 2, o.s();
                                case 4:
                                    if ((u = o.n()).done) {
                                        e.next = 44;
                                        break
                                    }
                                    if (l = u.value, l) {
                                        e.next = 8;
                                        break
                                    }
                                    return e.abrupt("continue", 42);
                                case 8:
                                    if (b = l["@_PageID"], d = l["ofd:FileLoc"], d = Object(c["m"])(d), t && -1 === d.indexOf(t) && (d = "".concat(t, "/").concat(d)), -1 === d.indexOf(a) && (d = "".concat(a, "/").concat(d)), !r.files[d]) {
                                        e.next = 42;
                                        break
                                    }
                                    return e.next = 16, T(r, d);
                                case 16:
                                    f = e.sent, h = [], h = h.concat(f["json"]["ofd:PageAnnot"]["ofd:Annot"]), i[b] || (i[b] = []), p = Object(s["a"])(h), e.prev = 21, p.s();
                                case 23:
                                    if ((v = p.n()).done) {
                                        e.next = 34;
                                        break
                                    }
                                    if (m = v.value, m) {
                                        e.next = 27;
                                        break
                                    }
                                    return e.abrupt("continue", 32);
                                case 27:
                                    g = m["@_Type"], y = !m["@_Visible"] || m["@_Visible"], x = m["ofd:Appearance"], w = {
                                        type: g,
                                        appearance: x,
                                        visible: y
                                    }, i[b].push(w);
                                case 32:
                                    e.next = 23;
                                    break;
                                case 34:
                                    e.next = 39;
                                    break;
                                case 36:
                                    e.prev = 36, e.t0 = e["catch"](21), p.e(e.t0);
                                case 39:
                                    return e.prev = 39, p.f(), e.finish(39);
                                case 42:
                                    e.next = 4;
                                    break;
                                case 44:
                                    e.next = 49;
                                    break;
                                case 46:
                                    e.prev = 46, e.t1 = e["catch"](2), o.e(e.t1);
                                case 49:
                                    return e.prev = 49, o.f(), e.finish(49);
                                case 52:
                                    return e.abrupt("return", i);
                                case 53:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[2, 46, 49, 52], [21, 36, 39, 42]])
                    })));
                    return function (t, n, s, a) {
                        return e.apply(this, arguments)
                    }
                }(), y = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, s, r, i, o, u, c, l, b, d, f, h, p;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (n = Object(a["a"])(t, 5), s = n[0], r = n[1], i = n[2], o = n[3], u = n[4], c = i["ofd:CommonData"]["ofd:DocumentRes"], l = {}, b = {}, d = {}, f = [], !c) {
                                        e.next = 23;
                                        break
                                    }
                                    if (-1 == c.indexOf(r) && (c = "".concat(r, "/").concat(c)), !s.files[c]) {
                                        e.next = 23;
                                        break
                                    }
                                    return e.next = 11, T(s, c);
                                case 11:
                                    return h = e.sent, p = h["json"]["ofd:Res"], e.next = 15, S(p);
                                case 15:
                                    return l = e.sent, e.next = 18, j(p);
                                case 18:
                                    return b = e.sent, e.next = 21, k(s, p, r);
                                case 21:
                                    d = e.sent, f = I(p);
                                case 23:
                                    return e.abrupt("return", [s, r, i, o, u, l, b, d, f]);
                                case 24:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), x = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, s, r, i, o, u, c, l, b, d, f, h, p, v, m, g;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (n = Object(a["a"])(t, 9), s = n[0], r = n[1], i = n[2], o = n[3], u = n[4], c = n[5], l = n[6], b = n[7], d = n[8], f = i["ofd:CommonData"]["ofd:PublicRes"], !f) {
                                        e.next = 22;
                                        break
                                    }
                                    if (-1 == f.indexOf(r) && (f = "".concat(r, "/").concat(f)), !s.files[f]) {
                                        e.next = 22;
                                        break
                                    }
                                    return e.next = 7, T(s, f);
                                case 7:
                                    return h = e.sent, p = h["json"]["ofd:Res"], e.next = 11, S(p);
                                case 11:
                                    return v = e.sent, c = Object.assign(c, v), e.next = 15, j(p);
                                case 15:
                                    return m = e.sent, l = Object.assign(l, m), e.next = 19, k(s, p, r);
                                case 19:
                                    g = e.sent, b = Object.assign(b, g), d = d.concat(I(p));
                                case 22:
                                    return e.abrupt("return", [s, r, i, o, u, c, l, b, d]);
                                case 23:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), w = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, r, i, o, u, c, l, b, d, f, h, p, v, m, g, y, x;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    n = Object(a["a"])(t, 9), r = n[0], i = n[1], o = n[2], u = n[3], c = n[4], l = n[5], b = n[6], d = n[7], f = n[8], h = o["ofd:CommonData"]["ofd:TemplatePage"], p = [], p = p.concat(h), v = {}, m = Object(s["a"])(p), e.prev = 6, m.s();
                                case 8:
                                    if ((g = m.n()).done) {
                                        e.next = 17;
                                        break
                                    }
                                    if (y = g.value, !y) {
                                        e.next = 15;
                                        break
                                    }
                                    return e.next = 13, C(r, y, i);
                                case 13:
                                    x = e.sent, v[Object.keys(x)[0]] = x[Object.keys(x)[0]];
                                case 15:
                                    e.next = 8;
                                    break;
                                case 17:
                                    e.next = 22;
                                    break;
                                case 19:
                                    e.prev = 19, e.t0 = e["catch"](6), m.e(e.t0);
                                case 22:
                                    return e.prev = 22, m.f(), e.finish(22);
                                case 25:
                                    return e.abrupt("return", [r, i, o, u, c, v, l, b, d, f]);
                                case 26:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[6, 19, 22, 25]])
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), O = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, r, i, o, u, c, l, b, d, f, h, p, v, m, g, y, x, w, O, S, j;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    n = Object(a["a"])(t, 10), r = n[0], i = n[1], o = n[2], u = n[3], c = n[4], l = n[5], b = n[6], d = n[7], f = n[8], h = n[9], p = o["ofd:Pages"]["ofd:Page"], v = [], v = v.concat(p), m = [], g = Object(s["a"])(v), e.prev = 6, g.s();
                                case 8:
                                    if ((y = g.n()).done) {
                                        e.next = 22;
                                        break
                                    }
                                    if (x = y.value, !x) {
                                        e.next = 20;
                                        break
                                    }
                                    return e.next = 13, C(r, x, i);
                                case 13:
                                    w = e.sent, O = Object.keys(w)[0], S = u[O], S && (w[O].stamp = S), j = c[O], j && (w[O].annotation = j), m.push(w);
                                case 20:
                                    e.next = 8;
                                    break;
                                case 22:
                                    e.next = 27;
                                    break;
                                case 24:
                                    e.prev = 24, e.t0 = e["catch"](6), g.e(e.t0);
                                case 27:
                                    return e.prev = 27, g.f(), e.finish(27);
                                case 30:
                                    return e.abrupt("return", {
                                        doc: i,
                                        document: o,
                                        pages: m,
                                        tpls: l,
                                        stampAnnot: u,
                                        fontResObj: b,
                                        drawParamResObj: d,
                                        multiMediaResObj: f,
                                        compositeGraphicUnits: h
                                    });
                                case 31:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[6, 24, 27, 30]])
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), S = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, a, r, i, o, u;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (n = t["ofd:Fonts"], a = {}, n) {
                                        r = [], r = r.concat(n["ofd:Font"]), i = Object(s["a"])(r);
                                        try {
                                            for (i.s(); !(o = i.n()).done;) u = o.value, u && (u["@_FamilyName"] ? a[u["@_ID"]] = u["@_FamilyName"] : a[u["@_ID"]] = u["@_FontName"])
                                        } catch (c) {
                                            i.e(c)
                                        } finally {
                                            i.f()
                                        }
                                    }
                                    return e.abrupt("return", a);
                                case 4:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), j = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                        var n, a, r, i, o, u;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (n = t["ofd:DrawParams"], a = {}, n) {
                                        r = [], r = r.concat(n["ofd:DrawParam"]), i = Object(s["a"])(r);
                                        try {
                                            for (i.s(); !(o = i.n()).done;) u = o.value, u && (a[u["@_ID"]] = {
                                                LineWidth: u["@_LineWidth"],
                                                FillColor: u["ofd:FillColor"] ? u["ofd:FillColor"]["@_Value"] : "",
                                                StrokeColor: u["ofd:StrokeColor"] ? u["ofd:StrokeColor"]["@_Value"] : "",
                                                relative: u["@_Relative"]
                                            })
                                        } catch (c) {
                                            i.e(c)
                                        } finally {
                                            i.f()
                                        }
                                    }
                                    return e.abrupt("return", a);
                                case 4:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t) {
                        return e.apply(this, arguments)
                    }
                }(), k = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n, a) {
                        var r, i, o, u, l, b, d, f, h, p, v;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (r = n["ofd:MultiMedias"], i = {}, !r) {
                                        e.next = 41;
                                        break
                                    }
                                    o = [], o = o.concat(r["ofd:MultiMedia"]), u = Object(s["a"])(o), e.prev = 6, u.s();
                                case 8:
                                    if ((l = u.n()).done) {
                                        e.next = 33;
                                        break
                                    }
                                    if (b = l.value, !b) {
                                        e.next = 31;
                                        break
                                    }
                                    if (d = b["ofd:MediaFile"], n["@_BaseLoc"] && -1 === d.indexOf(n["@_BaseLoc"]) && (d = "".concat(n["@_BaseLoc"], "/").concat(d)), -1 === d.indexOf(a) && (d = "".concat(a, "/").concat(d)), "image" !== b["@_Type"].toLowerCase()) {
                                        e.next = 30;
                                        break
                                    }
                                    if (f = b["@_Format"], h = Object(c["g"])(d), (!f || "gbig2" !== f.toLowerCase() && "jb2" !== f.toLowerCase()) && (!h || "jb2" !== h.toLowerCase() && "gbig2" !== h.toLowerCase())) {
                                        e.next = 24;
                                        break
                                    }
                                    return e.next = 20, P(t, d);
                                case 20:
                                    p = e.sent, i[b["@_ID"]] = p, e.next = 28;
                                    break;
                                case 24:
                                    return e.next = 26, F(t, d);
                                case 26:
                                    v = e.sent, i[b["@_ID"]] = {img: v, format: "png"};
                                case 28:
                                    e.next = 31;
                                    break;
                                case 30:
                                    i[b["@_ID"]] = d;
                                case 31:
                                    e.next = 8;
                                    break;
                                case 33:
                                    e.next = 38;
                                    break;
                                case 35:
                                    e.prev = 35, e.t0 = e["catch"](6), u.e(e.t0);
                                case 38:
                                    return e.prev = 38, u.f(), e.finish(38);
                                case 41:
                                    return e.abrupt("return", i);
                                case 42:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[6, 35, 38, 41]])
                    })));
                    return function (t, n, s) {
                        return e.apply(this, arguments)
                    }
                }(), I = function (e) {
                    var t = e["ofd:CompositeGraphicUnits"], n = [];
                    if (t) {
                        var a = [];
                        a = a.concat(t["ofd:CompositeGraphicUnit"]);
                        var r, i = Object(s["a"])(a);
                        try {
                            for (i.s(); !(r = i.n()).done;) {
                                var o = r.value;
                                o && n.push(o)
                            }
                        } catch (u) {
                            i.e(u)
                        } finally {
                            i.f()
                        }
                    }
                    return n
                }, C = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n, s) {
                        var a, r, i;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return a = n["@_BaseLoc"], -1 == a.indexOf(s) && (a = "".concat(s, "/").concat(a)), e.next = 4, T(t, a);
                                case 4:
                                    return r = e.sent, i = {}, i[n["@_ID"]] = {
                                        json: r["json"]["ofd:Page"],
                                        xml: r["xml"]
                                    }, e.abrupt("return", i);
                                case 8:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t, n, s) {
                        return e.apply(this, arguments)
                    }
                }(), _ = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n, a) {
                        var r, i, o, u, l, b, d, f, h;
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    if (r = [], !n) {
                                        e.next = 37;
                                        break
                                    }
                                    if (n = Object(c["m"])(n), -1 === n.indexOf(a) && (n = "".concat(a, "/").concat(n)), !t.files[n]) {
                                        e.next = 37;
                                        break
                                    }
                                    return e.next = 7, T(t, n);
                                case 7:
                                    i = e.sent, o = i["json"]["ofd:Signatures"]["ofd:Signature"], u = [], u = u.concat(o), l = Object(s["a"])(u), e.prev = 12, l.s();
                                case 14:
                                    if ((b = l.n()).done) {
                                        e.next = 29;
                                        break
                                    }
                                    if (d = b.value, !d) {
                                        e.next = 27;
                                        break
                                    }
                                    return f = d["@_BaseLoc"], h = d["@_ID"], f = Object(c["m"])(f), -1 === f.indexOf("Signs") && (f = "Signs/".concat(f), console.log("signatureLoc.indexOf('Signs') === -1")), -1 === f.indexOf(a) && (f = "".concat(a, "/").concat(f), console.log("signatureLoc.indexOf(doc) === -1")), e.t0 = r, e.next = 25, B(t, f, h);
                                case 25:
                                    e.t1 = e.sent, e.t0.push.call(e.t0, e.t1);
                                case 27:
                                    e.next = 14;
                                    break;
                                case 29:
                                    e.next = 34;
                                    break;
                                case 31:
                                    e.prev = 31, e.t2 = e["catch"](12), l.e(e.t2);
                                case 34:
                                    return e.prev = 34, l.f(), e.finish(34);
                                case 37:
                                    return console.log(r), e.abrupt("return", r);
                                case 39:
                                case"end":
                                    return e.stop()
                            }
                        }), e, null, [[12, 31, 34, 37]])
                    })));
                    return function (t, n, s) {
                        return e.apply(this, arguments)
                    }
                }(), A = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n) {
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.abrupt("return", t.files[n].async("uint8array"));
                                case 1:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t, n) {
                        return e.apply(this, arguments)
                    }
                }(), B = function () {
                    var t = Object(r["a"])(regeneratorRuntime.mark((function t(n, s, a) {
                        var i, o, u, c, l;
                        return regeneratorRuntime.wrap((function (t) {
                            while (1) switch (t.prev = t.next) {
                                case 0:
                                    return t.next = 2, T(n, s);
                                case 2:
                                    return i = t.sent, o = i["json"]["ofd:Signature"]["ofd:SignedValue"], o = o.toString().replace("/", ""), n.files[o] || (o = "".concat(s.substring(0, s.lastIndexOf("/")), "/").concat(o)), t.next = 8, Object(b["b"])(n, o);
                                case 8:
                                    return u = t.sent, c = i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:References"]["@_CheckMethod"], e.toBeChecked = new Map, l = new Array, i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:References"]["ofd:Reference"].forEach(function () {
                                        var e = Object(r["a"])(regeneratorRuntime.mark((function e(t) {
                                            var s, a, r;
                                            return regeneratorRuntime.wrap((function (e) {
                                                while (1) switch (e.prev = e.next) {
                                                    case 0:
                                                        if (0 != Object.keys(t).length && 0 != Object.keys(t["@_FileRef"]).length) {
                                                            e.next = 2;
                                                            break
                                                        }
                                                        return e.abrupt("return", !0);
                                                    case 2:
                                                        return s = t["ofd:CheckValue"], a = t["@_FileRef"].replace("/", ""), e.next = 6, A(n, a);
                                                    case 6:
                                                        r = e.sent, l.push({fileData: r, hashed: s, checkMethod: c});
                                                    case 8:
                                                    case"end":
                                                        return e.stop()
                                                }
                                            }), e)
                                        })));
                                        return function (t) {
                                            return e.apply(this, arguments)
                                        }
                                    }()), e.toBeChecked.set(a, l), t.abrupt("return", {
                                        stampAnnot: i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:StampAnnot"],
                                        sealObj: u,
                                        signedInfo: {
                                            signatureID: a,
                                            VerifyRet: u.verifyRet,
                                            Provider: i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:Provider"],
                                            SignatureMethod: i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:SignatureMethod"],
                                            SignatureDateTime: i["json"]["ofd:Signature"]["ofd:SignedInfo"]["ofd:SignatureDateTime"]
                                        }
                                    });
                                case 15:
                                case"end":
                                    return t.stop()
                            }
                        }), t)
                    })));
                    return function (e, n, s) {
                        return t.apply(this, arguments)
                    }
                }(), D = function (e) {
                    var t = this;
                    return new Promise((function (n, s) {
                        i["a"].call(t, Object(r["a"])(regeneratorRuntime.mark((function t() {
                            return regeneratorRuntime.wrap((function (t) {
                                while (1) switch (t.prev = t.next) {
                                    case 0:
                                        return t.next = 2, f(e.sealObj.ofdArray);
                                    case 2:
                                        return t.abrupt("return", t.sent);
                                    case 3:
                                    case"end":
                                        return t.stop()
                                }
                            }), t)
                        }))), h, p).then((function (e) {
                            n(e)
                        })).catch((function (e) {
                            s(e)
                        }))
                    }))
                }, T = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n) {
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.abrupt("return", new Promise((function (e, s) {
                                        t.files[n].async("string").then((function (t) {
                                            var n = {
                                                attributeNamePrefix: "@_",
                                                ignoreAttributes: !1,
                                                parseNodeValue: !1,
                                                trimValues: !1
                                            }, s = d.parse(t, n), a = {xml: t, json: s};
                                            e(a)
                                        }), (function (e) {
                                            s(e)
                                        }))
                                    })));
                                case 1:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t, n) {
                        return e.apply(this, arguments)
                    }
                }(), P = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n) {
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.abrupt("return", new Promise((function (e, s) {
                                        t.files[n].async("uint8array").then((function (t) {
                                            var n = new l["a"], s = n.parse(t);
                                            e({img: s, width: n.width, height: n.height, format: "gbig2"})
                                        }), (function (e) {
                                            s(e)
                                        }))
                                    })));
                                case 1:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t, n) {
                        return e.apply(this, arguments)
                    }
                }(), F = function () {
                    var e = Object(r["a"])(regeneratorRuntime.mark((function e(t, n) {
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.abrupt("return", new Promise((function (e, s) {
                                        t.files[n].async("base64").then((function (t) {
                                            var n = "data:image/png;base64," + t;
                                            e(n)
                                        }), (function (e) {
                                            s(e)
                                        }))
                                    })));
                                case 1:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    })));
                    return function (t, n) {
                        return e.apply(this, arguments)
                    }
                }()
        }).call(this, n("c8ba"))
    }, "6b33": function (e, t, n) {
        "use strict";
        n.d(t, "d", (function () {
            return a
        })), n.d(t, "b", (function () {
            return r
        })), n.d(t, "n", (function () {
            return c
        })), n.d(t, "o", (function () {
            return l
        })), n.d(t, "i", (function () {
            return b
        })), n.d(t, "f", (function () {
            return d
        })), n.d(t, "c", (function () {
            return h
        })), n.d(t, "m", (function () {
            return p
        })), n.d(t, "g", (function () {
            return v
        })), n.d(t, "h", (function () {
            return w
        })), n.d(t, "l", (function () {
            return O
        })), n.d(t, "k", (function () {
            return S
        })), n.d(t, "j", (function () {
            return j
        })), n.d(t, "e", (function () {
            return k
        })), n.d(t, "a", (function () {
            return I
        }));
        n("99af"), n("c975"), n("a15b"), n("baa5"), n("b64b"), n("d3b7"), n("ac1f"), n("25f0"), n("5319"), n("1276"), n("498a");
        var s = n("b85c"), a = function (e) {
                var t = e.split(" "), n = [], s = 0;
                while (s < t.length) {
                    if ("M" === t[s] || "S" === t[s]) {
                        var a = {type: "M", x: parseFloat(t[s + 1]), y: parseFloat(t[s + 2])};
                        s += 3, n.push(a)
                    }
                    if ("L" === t[s]) {
                        var r = {type: "L", x: parseFloat(t[s + 1]), y: parseFloat(t[s + 2])};
                        s += 3, n.push(r)
                    } else if ("C" === t[s]) {
                        var i = {type: "C", x: 0, y: 0};
                        n.push(i), s++
                    } else if ("B" === t[s]) {
                        var o = {
                            type: "B",
                            x1: parseFloat(t[s + 1]),
                            y1: parseFloat(t[s + 2]),
                            x2: parseFloat(t[s + 3]),
                            y2: parseFloat(t[s + 4]),
                            x3: parseFloat(t[s + 5]),
                            y3: parseFloat(t[s + 6])
                        };
                        s += 7, n.push(o)
                    } else if ("Q" === t[s]) {
                        var u = {
                            type: "Q",
                            x1: parseFloat(t[s + 1]),
                            y1: parseFloat(t[s + 2]),
                            x2: parseFloat(t[s + 3]),
                            y2: parseFloat(t[s + 4])
                        };
                        s += 5, n.push(u)
                    } else if ("A" === t[s]) {
                        var c = {
                            type: "A",
                            rx: parseFloat(t[s + 1]),
                            ry: parseFloat(t[s + 2]),
                            rotation: parseFloat(t[s + 3]),
                            arc: parseFloat(t[s + 4]),
                            sweep: parseFloat(t[s + 5]),
                            x: parseFloat(t[s + 6]),
                            y: parseFloat(t[s + 7])
                        };
                        s += 8, n.push(c)
                    } else s++
                }
                return n
            }, r = function (e) {
                for (var t = [], n = 0; n < e.length; n++) {
                    var s = e[n];
                    if ("M" === s.type || "L" === s.type || "C" === s.type) {
                        var a = 0, r = 0;
                        a = s.x, r = s.y, s.x = d(a), s.y = d(r), t.push(s)
                    } else if ("B" === s.type) {
                        var i = s.x1, o = s.y1, u = s.x2, c = s.y2, l = s.x3, b = s.y3,
                            f = {type: "B", x1: d(i), y1: d(o), x2: d(u), y2: d(c), x3: d(l), y3: d(b)};
                        t.push(f)
                    } else if ("Q" === s.type) {
                        var h = s.x1, p = s.y1, v = s.x2, m = s.y2, g = {type: "Q", x1: d(h), y1: d(p), x2: d(v), y2: d(m)};
                        t.push(g)
                    } else if ("A" === s.type) {
                        var y = s.rx, x = s.ry, w = s.rotation, O = s.arc, S = s.sweep, j = s.x, k = s.y,
                            I = {type: "A", rx: d(y), ry: d(x), rotation: w, arc: O, sweep: S, x: d(j), y: d(k)};
                        t.push(I)
                    }
                }
                return t
            }, i = function (e, t) {
                return e * t / 25.4
            }, o = 10, u = o, c = function (e) {
                o = e > 6 ? 6 : e
            }, l = function (e) {
                u = e > 1 ? e : 1, u = u > o ? o : u
            }, b = function () {
                return u
            }, d = function (e) {
                return i(e, 25.4 * u)
            }, f = function (e) {
                if (-1 === e.indexOf("g")) {
                    var t, n = [], a = Object(s["a"])(e.split(" "));
                    try {
                        for (a.s(); !(t = a.n()).done;) {
                            var r = t.value;
                            n.push(parseFloat(r))
                        }
                    } catch (p) {
                        a.e(p)
                    } finally {
                        a.f()
                    }
                    return n
                }
                var i, o = e.split(" "), u = !1, c = !1, l = 0, b = [], d = Object(s["a"])(o);
                try {
                    for (d.s(); !(i = d.n()).done;) {
                        var f = i.value;
                        if ("g" === f) u = !0; else {
                            if (!f || 0 == f.trim().length) continue;
                            if (u) l = parseInt(f), c = !0, u = !1; else if (c) {
                                for (var h = 0; h < l; h++) b.push(parseFloat(f));
                                c = !1
                            } else b.push(parseFloat(f))
                        }
                    }
                } catch (p) {
                    d.e(p)
                } finally {
                    d.f()
                }
                return b
            }, h = function (e) {
                var t = 0, n = 0, a = [];
                if (!e) return a;
                var r, i = Object(s["a"])(e);
                try {
                    for (i.s(); !(r = i.n()).done;) {
                        var o = r.value;
                        if (o) {
                            t = parseFloat(o["@_X"]), n = parseFloat(o["@_Y"]), isNaN(t) && (t = 0), isNaN(n) && (n = 0);
                            var u = [], c = [];
                            o["@_DeltaX"] && o["@_DeltaX"].length > 0 && (u = f(o["@_DeltaX"])), o["@_DeltaY"] && o["@_DeltaY"].length > 0 && (c = f(o["@_DeltaY"]));
                            var l = o["#text"];
                            if (l) {
                                l += "", l = y(l), l = l.replace(/&#x20;/g, " ");
                                for (var b = 0; b < l.length; b++) {
                                    b > 0 && u.length > 0 && (t += u[b - 1]), b > 0 && c.length > 0 && (n += c[b - 1]);
                                    var h = l.substring(b, b + 1), p = {x: d(t), y: d(n), text: h};
                                    a.push(p)
                                }
                            }
                        }
                    }
                } catch (v) {
                    i.e(v)
                } finally {
                    i.f()
                }
                return a
            }, p = function (e) {
                return e && 0 === e.indexOf("/") && (e = e.replace("/", "")), e
            }, v = function (e) {
                return e || "string" === typeof e ? e.substring(e.lastIndexOf(".") + 1) : ""
            }, m = /&\w+;|&#(\d+);/g,
            g = {"&lt;": "<", "&gt;": ">", "&amp;": "&", "&nbsp;": " ", "&quot;": '"', "&copy;": "", "&apos;": "'"},
            y = function (e) {
                return e = void 0 != e ? e : this.toString(), "string" != typeof e ? e : e.replace(m, (function (e, t) {
                    var n = g[e];
                    return void 0 == n && (n = isNaN(t) ? e : String.fromCharCode(160 == t ? 32 : t)), n
                }))
            }, x = {
                "楷体": "楷体, KaiTi, Kai, simkai",
                kaiti: "楷体, KaiTi, Kai, simkai",
                Kai: "楷体, KaiTi, Kai",
                simsun: "SimSun, simsun, Songti SC",
                "宋体": "SimSun, simsun, Songti SC",
                "黑体": "SimHei, STHeiti, simhei",
                "仿宋": "FangSong, STFangsong, simfang",
                "小标宋体": "sSun",
                "方正小标宋_gbk": "sSun",
                "仿宋_gb2312": "FangSong, STFangsong, simfang",
                "楷体_gb2312": "楷体, KaiTi, Kai, simkai",
                couriernew: "Courier New",
                "courier new": "Courier New"
            }, w = function (e) {
                x[e.toLowerCase()] && (e = x[e.toLowerCase()]);
                for (var t = 0, n = Object.keys(x); t < n.length; t++) {
                    var s = n[t];
                    if (-1 != e.toLowerCase().indexOf(s.toLowerCase())) return x[s]
                }
                return e
            }, O = function (e) {
                if (e) {
                    var t = e.split(" ");
                    return {x: parseFloat(t[0]), y: parseFloat(t[1]), w: parseFloat(t[2]), h: parseFloat(t[3])}
                }
                return null
            }, S = function (e) {
                var t = e.split(" ");
                return t
            }, j = function (e) {
                if (e) {
                    if (-1 !== e.indexOf("#")) return e = e.replace(/#/g, ""), e = e.replace(/ /g, ""), e = "#" + e.toString(), e;
                    var t = e.split(" ");
                    return "rgb(".concat(t[0], ", ").concat(t[1], ", ").concat(t[2], ")")
                }
                return "rgb(0, 0, 0)"
            }, k = function (e) {
                return {x: d(e.x), y: d(e.y), w: d(e.w), h: d(e.h)}
            }, I = function (e) {
                for (var t = [], n = 0, s = 0; s < 2 * e.length; s += 2) t[s >>> 3] |= parseInt(e[n], 10) << 24 - s % 8 * 4, n++;
                for (var a = [], r = 0; r < e.length; r++) {
                    var i = t[r >>> 2] >>> 24 - r % 4 * 8 & 255;
                    a.push((i >>> 4).toString(16)), a.push((15 & i).toString(16))
                }
                return a.join("")
            }
    }, "73fd": function (e, t, n) {
        "use strict";
        n.d(t, "a", (function () {
            return w
        }));
        n("99af"), n("d3b7"), n("fd87"), n("143c"), n("5cc6"), n("8a59"), n("84c3"), n("fb2c"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n("d139"), n("3a7b"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n("3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n("170b"), n("b39a"), n("72f7");
        var s = n("d4ec"), a = n("262e"), r = n("2caf"),
            i = (n("a623"), n("a15b"), n("fb6a"), n("b0c0"), n("a9e3"), n("8ba4"), n("ac1f"), n("25f0"), n("3ca3"), n("4d90"), n("5319"), n("ddb0"), n("2b3d"), n("bee2")),
            o = (n("53ca"), n("7f3b"), {ERRORS: 0, WARNINGS: 1, INFOS: 5}), u = o.WARNINGS;

        function c(e) {
            u >= o.INFOS && console.log("Info: ".concat(e))
        }

        function l(e) {
            throw new Error(e)
        }

        function b(e, t, n) {
            return Object.defineProperty(e, t, {value: n, enumerable: !0, configurable: !0, writable: !1}), n
        }

        var d = function () {
            function e(t) {
                this.constructor === e && l("Cannot initialize BaseException."), this.message = t, this.name = this.constructor.name
            }

            return e.prototype = new Error, e.constructor = e, e
        }();
        (function () {
            var e = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
        })(), n("90d7");

        function f(e) {
            return e <= 0 ? 0 : Math.ceil(Math.log2(e))
        }

        function h(e, t) {
            return e[t] << 24 >> 24
        }

        function p(e, t) {
            return e[t] << 8 | e[t + 1]
        }

        function v(e, t) {
            return (e[t] << 24 | e[t + 1] << 16 | e[t + 2] << 8 | e[t + 3]) >>> 0
        }

        var m = [{qe: 22017, nmps: 1, nlps: 1, switchFlag: 1}, {qe: 13313, nmps: 2, nlps: 6, switchFlag: 0}, {
                qe: 6145,
                nmps: 3,
                nlps: 9,
                switchFlag: 0
            }, {qe: 2753, nmps: 4, nlps: 12, switchFlag: 0}, {qe: 1313, nmps: 5, nlps: 29, switchFlag: 0}, {
                qe: 545,
                nmps: 38,
                nlps: 33,
                switchFlag: 0
            }, {qe: 22017, nmps: 7, nlps: 6, switchFlag: 1}, {qe: 21505, nmps: 8, nlps: 14, switchFlag: 0}, {
                qe: 18433,
                nmps: 9,
                nlps: 14,
                switchFlag: 0
            }, {qe: 14337, nmps: 10, nlps: 14, switchFlag: 0}, {qe: 12289, nmps: 11, nlps: 17, switchFlag: 0}, {
                qe: 9217,
                nmps: 12,
                nlps: 18,
                switchFlag: 0
            }, {qe: 7169, nmps: 13, nlps: 20, switchFlag: 0}, {qe: 5633, nmps: 29, nlps: 21, switchFlag: 0}, {
                qe: 22017,
                nmps: 15,
                nlps: 14,
                switchFlag: 1
            }, {qe: 21505, nmps: 16, nlps: 14, switchFlag: 0}, {qe: 20737, nmps: 17, nlps: 15, switchFlag: 0}, {
                qe: 18433,
                nmps: 18,
                nlps: 16,
                switchFlag: 0
            }, {qe: 14337, nmps: 19, nlps: 17, switchFlag: 0}, {qe: 13313, nmps: 20, nlps: 18, switchFlag: 0}, {
                qe: 12289,
                nmps: 21,
                nlps: 19,
                switchFlag: 0
            }, {qe: 10241, nmps: 22, nlps: 19, switchFlag: 0}, {qe: 9217, nmps: 23, nlps: 20, switchFlag: 0}, {
                qe: 8705,
                nmps: 24,
                nlps: 21,
                switchFlag: 0
            }, {qe: 7169, nmps: 25, nlps: 22, switchFlag: 0}, {qe: 6145, nmps: 26, nlps: 23, switchFlag: 0}, {
                qe: 5633,
                nmps: 27,
                nlps: 24,
                switchFlag: 0
            }, {qe: 5121, nmps: 28, nlps: 25, switchFlag: 0}, {qe: 4609, nmps: 29, nlps: 26, switchFlag: 0}, {
                qe: 4353,
                nmps: 30,
                nlps: 27,
                switchFlag: 0
            }, {qe: 2753, nmps: 31, nlps: 28, switchFlag: 0}, {qe: 2497, nmps: 32, nlps: 29, switchFlag: 0}, {
                qe: 2209,
                nmps: 33,
                nlps: 30,
                switchFlag: 0
            }, {qe: 1313, nmps: 34, nlps: 31, switchFlag: 0}, {qe: 1089, nmps: 35, nlps: 32, switchFlag: 0}, {
                qe: 673,
                nmps: 36,
                nlps: 33,
                switchFlag: 0
            }, {qe: 545, nmps: 37, nlps: 34, switchFlag: 0}, {qe: 321, nmps: 38, nlps: 35, switchFlag: 0}, {
                qe: 273,
                nmps: 39,
                nlps: 36,
                switchFlag: 0
            }, {qe: 133, nmps: 40, nlps: 37, switchFlag: 0}, {qe: 73, nmps: 41, nlps: 38, switchFlag: 0}, {
                qe: 37,
                nmps: 42,
                nlps: 39,
                switchFlag: 0
            }, {qe: 21, nmps: 43, nlps: 40, switchFlag: 0}, {qe: 9, nmps: 44, nlps: 41, switchFlag: 0}, {
                qe: 5,
                nmps: 45,
                nlps: 42,
                switchFlag: 0
            }, {qe: 1, nmps: 45, nlps: 43, switchFlag: 0}, {qe: 22017, nmps: 46, nlps: 46, switchFlag: 0}],
            g = function () {
                function e(t, n, a) {
                    Object(s["a"])(this, e), this.data = t, this.bp = n, this.dataEnd = a, this.chigh = t[n], this.clow = 0, this.byteIn(), this.chigh = this.chigh << 7 & 65535 | this.clow >> 9 & 127, this.clow = this.clow << 7 & 65535, this.ct -= 7, this.a = 32768
                }

                return Object(i["a"])(e, [{
                    key: "byteIn", value: function () {
                        var e = this.data, t = this.bp;
                        255 === e[t] ? e[t + 1] > 143 ? (this.clow += 65280, this.ct = 8) : (t++, this.clow += e[t] << 9, this.ct = 7, this.bp = t) : (t++, this.clow += t < this.dataEnd ? e[t] << 8 : 65280, this.ct = 8, this.bp = t), this.clow > 65535 && (this.chigh += this.clow >> 16, this.clow &= 65535)
                    }
                }, {
                    key: "readBit", value: function (e, t) {
                        var n, s = e[t] >> 1, a = 1 & e[t], r = m[s], i = r.qe, o = this.a - i;
                        if (this.chigh < i) o < i ? (o = i, n = a, s = r.nmps) : (o = i, n = 1 ^ a, 1 === r.switchFlag && (a = n), s = r.nlps); else {
                            if (this.chigh -= i, 0 !== (32768 & o)) return this.a = o, a;
                            o < i ? (n = 1 ^ a, 1 === r.switchFlag && (a = n), s = r.nlps) : (n = a, s = r.nmps)
                        }
                        do {
                            0 === this.ct && this.byteIn(), o <<= 1, this.chigh = this.chigh << 1 & 65535 | this.clow >> 15 & 1, this.clow = this.clow << 1 & 65535, this.ct--
                        } while (0 === (32768 & o));
                        return this.a = o, e[t] = s << 1 | a, n
                    }
                }]), e
            }(), y = function () {
                var e = -2, t = -1, n = 0, s = 1, a = 2, r = 3, i = 4, o = 5, u = 6, l = 7, b = 8,
                    d = [[-1, -1], [-1, -1], [7, b], [7, l], [6, u], [6, u], [6, o], [6, o], [4, n], [4, n], [4, n], [4, n], [4, n], [4, n], [4, n], [4, n], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, s], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, i], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [3, r], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a], [1, a]],
                    f = [[-1, -1], [12, e], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [11, 1792], [11, 1792], [12, 1984], [12, 2048], [12, 2112], [12, 2176], [12, 2240], [12, 2304], [11, 1856], [11, 1856], [11, 1920], [11, 1920], [12, 2368], [12, 2432], [12, 2496], [12, 2560]],
                    h = [[-1, -1], [-1, -1], [-1, -1], [-1, -1], [8, 29], [8, 29], [8, 30], [8, 30], [8, 45], [8, 45], [8, 46], [8, 46], [7, 22], [7, 22], [7, 22], [7, 22], [7, 23], [7, 23], [7, 23], [7, 23], [8, 47], [8, 47], [8, 48], [8, 48], [6, 13], [6, 13], [6, 13], [6, 13], [6, 13], [6, 13], [6, 13], [6, 13], [7, 20], [7, 20], [7, 20], [7, 20], [8, 33], [8, 33], [8, 34], [8, 34], [8, 35], [8, 35], [8, 36], [8, 36], [8, 37], [8, 37], [8, 38], [8, 38], [7, 19], [7, 19], [7, 19], [7, 19], [8, 31], [8, 31], [8, 32], [8, 32], [6, 1], [6, 1], [6, 1], [6, 1], [6, 1], [6, 1], [6, 1], [6, 1], [6, 12], [6, 12], [6, 12], [6, 12], [6, 12], [6, 12], [6, 12], [6, 12], [8, 53], [8, 53], [8, 54], [8, 54], [7, 26], [7, 26], [7, 26], [7, 26], [8, 39], [8, 39], [8, 40], [8, 40], [8, 41], [8, 41], [8, 42], [8, 42], [8, 43], [8, 43], [8, 44], [8, 44], [7, 21], [7, 21], [7, 21], [7, 21], [7, 28], [7, 28], [7, 28], [7, 28], [8, 61], [8, 61], [8, 62], [8, 62], [8, 63], [8, 63], [8, 0], [8, 0], [8, 320], [8, 320], [8, 384], [8, 384], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 10], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [5, 11], [7, 27], [7, 27], [7, 27], [7, 27], [8, 59], [8, 59], [8, 60], [8, 60], [9, 1472], [9, 1536], [9, 1600], [9, 1728], [7, 18], [7, 18], [7, 18], [7, 18], [7, 24], [7, 24], [7, 24], [7, 24], [8, 49], [8, 49], [8, 50], [8, 50], [8, 51], [8, 51], [8, 52], [8, 52], [7, 25], [7, 25], [7, 25], [7, 25], [8, 55], [8, 55], [8, 56], [8, 56], [8, 57], [8, 57], [8, 58], [8, 58], [6, 192], [6, 192], [6, 192], [6, 192], [6, 192], [6, 192], [6, 192], [6, 192], [6, 1664], [6, 1664], [6, 1664], [6, 1664], [6, 1664], [6, 1664], [6, 1664], [6, 1664], [8, 448], [8, 448], [8, 512], [8, 512], [9, 704], [9, 768], [8, 640], [8, 640], [8, 576], [8, 576], [9, 832], [9, 896], [9, 960], [9, 1024], [9, 1088], [9, 1152], [9, 1216], [9, 1280], [9, 1344], [9, 1408], [7, 256], [7, 256], [7, 256], [7, 256], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 2], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [4, 3], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 128], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 8], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [5, 9], [6, 16], [6, 16], [6, 16], [6, 16], [6, 16], [6, 16], [6, 16], [6, 16], [6, 17], [6, 17], [6, 17], [6, 17], [6, 17], [6, 17], [6, 17], [6, 17], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 4], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [4, 5], [6, 14], [6, 14], [6, 14], [6, 14], [6, 14], [6, 14], [6, 14], [6, 14], [6, 15], [6, 15], [6, 15], [6, 15], [6, 15], [6, 15], [6, 15], [6, 15], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [5, 64], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 6], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7], [4, 7]],
                    p = [[-1, -1], [-1, -1], [12, e], [12, e], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [-1, -1], [11, 1792], [11, 1792], [11, 1792], [11, 1792], [12, 1984], [12, 1984], [12, 2048], [12, 2048], [12, 2112], [12, 2112], [12, 2176], [12, 2176], [12, 2240], [12, 2240], [12, 2304], [12, 2304], [11, 1856], [11, 1856], [11, 1856], [11, 1856], [11, 1920], [11, 1920], [11, 1920], [11, 1920], [12, 2368], [12, 2368], [12, 2432], [12, 2432], [12, 2496], [12, 2496], [12, 2560], [12, 2560], [10, 18], [10, 18], [10, 18], [10, 18], [10, 18], [10, 18], [10, 18], [10, 18], [12, 52], [12, 52], [13, 640], [13, 704], [13, 768], [13, 832], [12, 55], [12, 55], [12, 56], [12, 56], [13, 1280], [13, 1344], [13, 1408], [13, 1472], [12, 59], [12, 59], [12, 60], [12, 60], [13, 1536], [13, 1600], [11, 24], [11, 24], [11, 24], [11, 24], [11, 25], [11, 25], [11, 25], [11, 25], [13, 1664], [13, 1728], [12, 320], [12, 320], [12, 384], [12, 384], [12, 448], [12, 448], [13, 512], [13, 576], [12, 53], [12, 53], [12, 54], [12, 54], [13, 896], [13, 960], [13, 1024], [13, 1088], [13, 1152], [13, 1216], [10, 64], [10, 64], [10, 64], [10, 64], [10, 64], [10, 64], [10, 64], [10, 64]],
                    v = [[8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [8, 13], [11, 23], [11, 23], [12, 50], [12, 51], [12, 44], [12, 45], [12, 46], [12, 47], [12, 57], [12, 58], [12, 61], [12, 256], [10, 16], [10, 16], [10, 16], [10, 16], [10, 17], [10, 17], [10, 17], [10, 17], [12, 48], [12, 49], [12, 62], [12, 63], [12, 30], [12, 31], [12, 32], [12, 33], [12, 40], [12, 41], [11, 22], [11, 22], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [8, 14], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 10], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [7, 11], [9, 15], [9, 15], [9, 15], [9, 15], [9, 15], [9, 15], [9, 15], [9, 15], [12, 128], [12, 192], [12, 26], [12, 27], [12, 28], [12, 29], [11, 19], [11, 19], [11, 20], [11, 20], [12, 34], [12, 35], [12, 36], [12, 37], [12, 38], [12, 39], [11, 21], [11, 21], [12, 42], [12, 43], [10, 0], [10, 0], [10, 0], [10, 0], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12], [7, 12]],
                    m = [[-1, -1], [-1, -1], [-1, -1], [-1, -1], [6, 9], [6, 8], [5, 7], [5, 7], [4, 6], [4, 6], [4, 6], [4, 6], [4, 5], [4, 5], [4, 5], [4, 5], [3, 1], [3, 1], [3, 1], [3, 1], [3, 1], [3, 1], [3, 1], [3, 1], [3, 4], [3, 4], [3, 4], [3, 4], [3, 4], [3, 4], [3, 4], [3, 4], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 3], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2], [2, 2]];

                function g(e) {
                    var t = arguments.length > 1 && void 0 !== arguments[1] ? arguments[1] : {};
                    if (!e || "function" !== typeof e.next) throw new Error('CCITTFaxDecoder - invalid "source" parameter.');
                    this.source = e, this.eof = !1, this.encoding = t.K || 0, this.eoline = t.EndOfLine || !1, this.byteAlign = t.EncodedByteAlign || !1, this.columns = t.Columns || 1728, this.rows = t.Rows || 0;
                    var n, s = t.EndOfBlock;
                    null !== s && void 0 !== s || (s = !0), this.eoblock = s, this.black = t.BlackIs1 || !1, this.codingLine = new Uint32Array(this.columns + 1), this.refLine = new Uint32Array(this.columns + 2), this.codingLine[0] = this.columns, this.codingPos = 0, this.row = 0, this.nextLine2D = this.encoding < 0, this.inputBits = 0, this.inputBuf = 0, this.outputBits = 0, this.rowsDone = !1;
                    while (0 === (n = this._lookBits(12))) this._eatBits(1);
                    1 === n && this._eatBits(12), this.encoding > 0 && (this.nextLine2D = !this._lookBits(1), this._eatBits(1))
                }

                return g.prototype = {
                    readNextChar: function () {
                        if (this.eof) return -1;
                        var e, d, f, h, p, v = this.refLine, m = this.codingLine, g = this.columns;
                        if (0 === this.outputBits) {
                            if (this.rowsDone && (this.eof = !0), this.eof) return -1;
                            var y, x, w;
                            if (this.err = !1, this.nextLine2D) {
                                for (h = 0; m[h] < g; ++h) v[h] = m[h];
                                v[h++] = g, v[h] = g, m[0] = 0, this.codingPos = 0, e = 0, d = 0;
                                while (m[this.codingPos] < g) switch (y = this._getTwoDimCode(), y) {
                                    case n:
                                        this._addPixels(v[e + 1], d), v[e + 1] < g && (e += 2);
                                        break;
                                    case s:
                                        if (y = x = 0, d) {
                                            do {
                                                y += w = this._getBlackCode()
                                            } while (w >= 64);
                                            do {
                                                x += w = this._getWhiteCode()
                                            } while (w >= 64)
                                        } else {
                                            do {
                                                y += w = this._getWhiteCode()
                                            } while (w >= 64);
                                            do {
                                                x += w = this._getBlackCode()
                                            } while (w >= 64)
                                        }
                                        this._addPixels(m[this.codingPos] + y, d), m[this.codingPos] < g && this._addPixels(m[this.codingPos] + x, 1 ^ d);
                                        while (v[e] <= m[this.codingPos] && v[e] < g) e += 2;
                                        break;
                                    case l:
                                        if (this._addPixels(v[e] + 3, d), d ^= 1, m[this.codingPos] < g) {
                                            ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case o:
                                        if (this._addPixels(v[e] + 2, d), d ^= 1, m[this.codingPos] < g) {
                                            ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case r:
                                        if (this._addPixels(v[e] + 1, d), d ^= 1, m[this.codingPos] < g) {
                                            ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case a:
                                        if (this._addPixels(v[e], d), d ^= 1, m[this.codingPos] < g) {
                                            ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case b:
                                        if (this._addPixelsNeg(v[e] - 3, d), d ^= 1, m[this.codingPos] < g) {
                                            e > 0 ? --e : ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case u:
                                        if (this._addPixelsNeg(v[e] - 2, d), d ^= 1, m[this.codingPos] < g) {
                                            e > 0 ? --e : ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case i:
                                        if (this._addPixelsNeg(v[e] - 1, d), d ^= 1, m[this.codingPos] < g) {
                                            e > 0 ? --e : ++e;
                                            while (v[e] <= m[this.codingPos] && v[e] < g) e += 2
                                        }
                                        break;
                                    case t:
                                        this._addPixels(g, 0), this.eof = !0;
                                        break;
                                    default:
                                        c("bad 2d code"), this._addPixels(g, 0), this.err = !0
                                }
                            } else {
                                m[0] = 0, this.codingPos = 0, d = 0;
                                while (m[this.codingPos] < g) {
                                    if (y = 0, d) do {
                                        y += w = this._getBlackCode()
                                    } while (w >= 64); else do {
                                        y += w = this._getWhiteCode()
                                    } while (w >= 64);
                                    this._addPixels(m[this.codingPos] + y, d), d ^= 1
                                }
                            }
                            var O = !1;
                            if (this.byteAlign && (this.inputBits &= -8), this.eoblock || this.row !== this.rows - 1) {
                                if (y = this._lookBits(12), this.eoline) while (y !== t && 1 !== y) this._eatBits(1), y = this._lookBits(12); else while (0 === y) this._eatBits(1), y = this._lookBits(12);
                                1 === y ? (this._eatBits(12), O = !0) : y === t && (this.eof = !0)
                            } else this.rowsDone = !0;
                            if (!this.eof && this.encoding > 0 && !this.rowsDone && (this.nextLine2D = !this._lookBits(1), this._eatBits(1)), this.eoblock && O && this.byteAlign) {
                                if (y = this._lookBits(12), 1 === y) {
                                    if (this._eatBits(12), this.encoding > 0 && (this._lookBits(1), this._eatBits(1)), this.encoding >= 0) for (h = 0; h < 4; ++h) y = this._lookBits(12), 1 !== y && c("bad rtc code: " + y), this._eatBits(12), this.encoding > 0 && (this._lookBits(1), this._eatBits(1));
                                    this.eof = !0
                                }
                            } else if (this.err && this.eoline) {
                                while (1) {
                                    if (y = this._lookBits(13), y === t) return this.eof = !0, -1;
                                    if (y >> 1 === 1) break;
                                    this._eatBits(1)
                                }
                                this._eatBits(12), this.encoding > 0 && (this._eatBits(1), this.nextLine2D = !(1 & y))
                            }
                            m[0] > 0 ? this.outputBits = m[this.codingPos = 0] : this.outputBits = m[this.codingPos = 1], this.row++
                        }
                        if (this.outputBits >= 8) p = 1 & this.codingPos ? 0 : 255, this.outputBits -= 8, 0 === this.outputBits && m[this.codingPos] < g && (this.codingPos++, this.outputBits = m[this.codingPos] - m[this.codingPos - 1]); else {
                            f = 8, p = 0;
                            do {
                                this.outputBits > f ? (p <<= f, 1 & this.codingPos || (p |= 255 >> 8 - f), this.outputBits -= f, f = 0) : (p <<= this.outputBits, 1 & this.codingPos || (p |= 255 >> 8 - this.outputBits), f -= this.outputBits, this.outputBits = 0, m[this.codingPos] < g ? (this.codingPos++, this.outputBits = m[this.codingPos] - m[this.codingPos - 1]) : f > 0 && (p <<= f, f = 0))
                            } while (f)
                        }
                        return this.black && (p ^= 255), p
                    }, _addPixels: function (e, t) {
                        var n = this.codingLine, s = this.codingPos;
                        e > n[s] && (e > this.columns && (c("row is wrong length"), this.err = !0, e = this.columns), 1 & s ^ t && ++s, n[s] = e), this.codingPos = s
                    }, _addPixelsNeg: function (e, t) {
                        var n = this.codingLine, s = this.codingPos;
                        if (e > n[s]) e > this.columns && (c("row is wrong length"), this.err = !0, e = this.columns), 1 & s ^ t && ++s, n[s] = e; else if (e < n[s]) {
                            e < 0 && (c("invalid code"), this.err = !0, e = 0);
                            while (s > 0 && e < n[s - 1]) --s;
                            n[s] = e
                        }
                        this.codingPos = s
                    }, _findTableCode: function (e, n, s, a) {
                        for (var r = a || 0, i = e; i <= n; ++i) {
                            var o = this._lookBits(i);
                            if (o === t) return [!0, 1, !1];
                            if (i < n && (o <<= n - i), !r || o >= r) {
                                var u = s[o - r];
                                if (u[0] === i) return this._eatBits(i), [!0, u[1], !0]
                            }
                        }
                        return [!1, 0, !1]
                    }, _getTwoDimCode: function () {
                        var e, n = 0;
                        if (this.eoblock) {
                            if (n = this._lookBits(7), e = d[n], e && e[0] > 0) return this._eatBits(e[0]), e[1]
                        } else {
                            var s = this._findTableCode(1, 7, d);
                            if (s[0] && s[2]) return s[1]
                        }
                        return c("Bad two dim code"), t
                    }, _getWhiteCode: function () {
                        var e, n = 0;
                        if (this.eoblock) {
                            if (n = this._lookBits(12), n === t) return 1;
                            if (e = n >> 5 === 0 ? f[n] : h[n >> 3], e[0] > 0) return this._eatBits(e[0]), e[1]
                        } else {
                            var s = this._findTableCode(1, 9, h);
                            if (s[0]) return s[1];
                            if (s = this._findTableCode(11, 12, f), s[0]) return s[1]
                        }
                        return c("bad white code"), this._eatBits(1), 1
                    }, _getBlackCode: function () {
                        var e, n;
                        if (this.eoblock) {
                            if (e = this._lookBits(13), e === t) return 1;
                            if (n = e >> 7 === 0 ? p[e] : e >> 9 === 0 && e >> 7 !== 0 ? v[(e >> 1) - 64] : m[e >> 7], n[0] > 0) return this._eatBits(n[0]), n[1]
                        } else {
                            var s = this._findTableCode(2, 6, m);
                            if (s[0]) return s[1];
                            if (s = this._findTableCode(7, 12, v, 64), s[0]) return s[1];
                            if (s = this._findTableCode(10, 13, p), s[0]) return s[1]
                        }
                        return c("bad black code"), this._eatBits(1), 1
                    }, _lookBits: function (e) {
                        var n;
                        while (this.inputBits < e) {
                            if (-1 === (n = this.source.next())) return 0 === this.inputBits ? t : this.inputBuf << e - this.inputBits & 65535 >> 16 - e;
                            this.inputBuf = this.inputBuf << 8 | n, this.inputBits += 8
                        }
                        return this.inputBuf >> this.inputBits - e & 65535 >> 16 - e
                    }, _eatBits: function (e) {
                        (this.inputBits -= e) < 0 && (this.inputBits = 0)
                    }
                }, g
            }(), x = function (e) {
                Object(a["a"])(n, e);
                var t = Object(r["a"])(n);

                function n(e) {
                    return Object(s["a"])(this, n), t.call(this, "JBIG2 error: ".concat(e))
                }

                return n
            }(d), w = function () {
                function e() {
                }

                function t(e, t, n) {
                    this.data = e, this.start = t, this.end = n
                }

                function n(e, t, n) {
                    var s = e.getContexts(t), a = 1;

                    function r(e) {
                        for (var t = 0, r = 0; r < e; r++) {
                            var i = n.readBit(s, a);
                            a = a < 256 ? a << 1 | i : 511 & (a << 1 | i) | 256, t = t << 1 | i
                        }
                        return t >>> 0
                    }

                    var i = r(1),
                        o = r(1) ? r(1) ? r(1) ? r(1) ? r(1) ? r(32) + 4436 : r(12) + 340 : r(8) + 84 : r(6) + 20 : r(4) + 4 : r(2);
                    return 0 === i ? o : o > 0 ? -o : null
                }

                function s(e, t, n) {
                    for (var s = e.getContexts("IAID"), a = 1, r = 0; r < n; r++) {
                        var i = t.readBit(s, a);
                        a = a << 1 | i
                    }
                    return n < 31 ? a & (1 << n) - 1 : 2147483647 & a
                }

                e.prototype = {
                    getContexts: function (e) {
                        return e in this ? this[e] : this[e] = new Int8Array(65536)
                    }
                }, t.prototype = {
                    get decoder() {
                        var e = new g(this.data, this.start, this.end);
                        return b(this, "decoder", e)
                    }, get contextCache() {
                        var t = new e;
                        return b(this, "contextCache", t)
                    }
                };
                var a = ["SymbolDictionary", null, null, null, "IntermediateTextRegion", null, "ImmediateTextRegion", "ImmediateLosslessTextRegion", null, null, null, null, null, null, null, null, "PatternDictionary", null, null, null, "IntermediateHalftoneRegion", null, "ImmediateHalftoneRegion", "ImmediateLosslessHalftoneRegion", null, null, null, null, null, null, null, null, null, null, null, null, "IntermediateGenericRegion", null, "ImmediateGenericRegion", "ImmediateLosslessGenericRegion", "IntermediateGenericRefinementRegion", null, "ImmediateGenericRefinementRegion", "ImmediateLosslessGenericRefinementRegion", null, null, null, null, "PageInformation", "EndOfPage", "EndOfStripe", "EndOfFile", "Profiles", "Tables", null, null, null, null, null, null, null, null, "Extension"],
                    r = [[{x: -1, y: -2}, {x: 0, y: -2}, {x: 1, y: -2}, {x: -2, y: -1}, {x: -1, y: -1}, {
                        x: 0,
                        y: -1
                    }, {x: 1, y: -1}, {x: 2, y: -1}, {x: -4, y: 0}, {x: -3, y: 0}, {x: -2, y: 0}, {x: -1, y: 0}], [{
                        x: -1,
                        y: -2
                    }, {x: 0, y: -2}, {x: 1, y: -2}, {x: 2, y: -2}, {x: -2, y: -1}, {x: -1, y: -1}, {x: 0, y: -1}, {
                        x: 1,
                        y: -1
                    }, {x: 2, y: -1}, {x: -3, y: 0}, {x: -2, y: 0}, {x: -1, y: 0}], [{x: -1, y: -2}, {x: 0, y: -2}, {
                        x: 1,
                        y: -2
                    }, {x: -2, y: -1}, {x: -1, y: -1}, {x: 0, y: -1}, {x: 1, y: -1}, {x: -2, y: 0}, {x: -1, y: 0}], [{
                        x: -3,
                        y: -1
                    }, {x: -2, y: -1}, {x: -1, y: -1}, {x: 0, y: -1}, {x: 1, y: -1}, {x: -4, y: 0}, {x: -3, y: 0}, {
                        x: -2,
                        y: 0
                    }, {x: -1, y: 0}]], i = [{
                        coding: [{x: 0, y: -1}, {x: 1, y: -1}, {x: -1, y: 0}],
                        reference: [{x: 0, y: -1}, {x: 1, y: -1}, {x: -1, y: 0}, {x: 0, y: 0}, {x: 1, y: 0}, {
                            x: -1,
                            y: 1
                        }, {x: 0, y: 1}, {x: 1, y: 1}]
                    }, {
                        coding: [{x: -1, y: -1}, {x: 0, y: -1}, {x: 1, y: -1}, {x: -1, y: 0}],
                        reference: [{x: 0, y: -1}, {x: -1, y: 0}, {x: 0, y: 0}, {x: 1, y: 0}, {x: 0, y: 1}, {x: 1, y: 1}]
                    }], o = [39717, 1941, 229, 405], u = [32, 8];

                function c(e, t, n) {
                    var s, a, r, i, o, u, c, l = n.decoder, b = n.contextCache.getContexts("GB"), d = [], f = 31735;
                    for (a = 0; a < t; a++) for (o = d[a] = new Uint8Array(e), u = a < 1 ? o : d[a - 1], c = a < 2 ? o : d[a - 2], s = c[0] << 13 | c[1] << 12 | c[2] << 11 | u[0] << 7 | u[1] << 6 | u[2] << 5 | u[3] << 4, r = 0; r < e; r++) o[r] = i = l.readBit(b, s), s = (s & f) << 1 | (r + 3 < e ? c[r + 3] << 11 : 0) | (r + 4 < e ? u[r + 4] << 4 : 0) | i;
                    return d
                }

                function l(e, t, n, s, a, i, u, l) {
                    if (e) {
                        var b = new M(l.data, l.start, l.end);
                        return W(b, t, n, !1)
                    }
                    if (0 === s && !i && !a && 4 === u.length && 3 === u[0].x && -1 === u[0].y && -3 === u[1].x && -1 === u[1].y && 2 === u[2].x && -2 === u[2].y && -2 === u[3].x && -2 === u[3].y) return c(t, n, l);
                    var d = !!i, f = r[s].concat(u);
                    f.sort((function (e, t) {
                        return e.y - t.y || e.x - t.x
                    }));
                    var h, p, v = f.length, m = new Int8Array(v), g = new Int8Array(v), y = [], x = 0, w = 0, O = 0, S = 0;
                    for (p = 0; p < v; p++) m[p] = f[p].x, g[p] = f[p].y, w = Math.min(w, f[p].x), O = Math.max(O, f[p].x), S = Math.min(S, f[p].y), p < v - 1 && f[p].y === f[p + 1].y && f[p].x === f[p + 1].x - 1 ? x |= 1 << v - 1 - p : y.push(p);
                    var j = y.length, k = new Int8Array(j), I = new Int8Array(j), C = new Uint16Array(j);
                    for (h = 0; h < j; h++) p = y[h], k[h] = f[p].x, I[h] = f[p].y, C[h] = 1 << v - 1 - p;
                    for (var _, A, B, D, T, P = -w, F = -S, R = t - O, E = o[s], L = new Uint8Array(t), V = [], N = l.decoder, H = l.contextCache.getContexts("GB"), q = 0, U = 0, $ = 0; $ < n; $++) {
                        if (a) {
                            var z = N.readBit(H, E);
                            if (q ^= z, q) {
                                V.push(L);
                                continue
                            }
                        }
                        for (L = new Uint8Array(L), V.push(L), _ = 0; _ < t; _++) if (d && i[$][_]) L[_] = 0; else {
                            if (_ >= P && _ < R && $ >= F) for (U = U << 1 & x, p = 0; p < j; p++) A = $ + I[p], B = _ + k[p], D = V[A][B], D && (D = C[p], U |= D); else for (U = 0, T = v - 1, p = 0; p < v; p++, T--) B = _ + m[p], B >= 0 && B < t && (A = $ + g[p], A >= 0 && (D = V[A][B], D && (U |= D << T)));
                            var G = N.readBit(H, U);
                            L[_] = G
                        }
                    }
                    return V
                }

                function d(e, t, n, s, a, r, o, c, l) {
                    var b = i[n].coding;
                    0 === n && (b = b.concat([c[0]]));
                    var d, f = b.length, h = new Int32Array(f), p = new Int32Array(f);
                    for (d = 0; d < f; d++) h[d] = b[d].x, p[d] = b[d].y;
                    var v = i[n].reference;
                    0 === n && (v = v.concat([c[1]]));
                    var m = v.length, g = new Int32Array(m), y = new Int32Array(m);
                    for (d = 0; d < m; d++) g[d] = v[d].x, y[d] = v[d].y;
                    for (var w = s[0].length, O = s.length, S = u[n], j = [], k = l.decoder, I = l.contextCache.getContexts("GR"), C = 0, _ = 0; _ < t; _++) {
                        if (o) {
                            var A = k.readBit(I, S);
                            if (C ^= A, C) throw new x("prediction is not supported")
                        }
                        var B = new Uint8Array(e);
                        j.push(B);
                        for (var D = 0; D < e; D++) {
                            var T, P, F = 0;
                            for (d = 0; d < f; d++) T = _ + p[d], P = D + h[d], T < 0 || P < 0 || P >= e ? F <<= 1 : F = F << 1 | j[T][P];
                            for (d = 0; d < m; d++) T = _ + y[d] - r, P = D + g[d] - a, T < 0 || T >= O || P < 0 || P >= w ? F <<= 1 : F = F << 1 | s[T][P];
                            var R = k.readBit(I, F);
                            B[D] = R
                        }
                    }
                    return j
                }

                function m(e, t, a, r, i, o, u, c, b, h, p, v) {
                    if (e && t) throw new x("symbol refinement with Huffman is not supported");
                    var m, g, y = [], O = 0, S = f(a.length + r), j = p.decoder, k = p.contextCache;
                    e && (m = V(1), g = [], S = Math.max(S, 1));
                    while (y.length < r) {
                        var I = e ? o.tableDeltaHeight.decode(v) : n(k, "IADH", j);
                        O += I;
                        var C = 0, _ = 0, A = e ? g.length : 0;
                        while (1) {
                            var B, D = e ? o.tableDeltaWidth.decode(v) : n(k, "IADW", j);
                            if (null === D) break;
                            if (C += D, _ += C, t) {
                                var T = n(k, "IAAI", j);
                                if (T > 1) B = w(e, t, C, O, 0, T, 1, a.concat(y), S, 0, 0, 1, 0, o, b, h, p, 0, v); else {
                                    var P = s(k, j, S), F = n(k, "IARDX", j), R = n(k, "IARDY", j),
                                        E = P < a.length ? a[P] : y[P - a.length];
                                    B = d(C, O, b, E, F, R, !1, h, p)
                                }
                                y.push(B)
                            } else e ? g.push(C) : (B = l(!1, C, O, u, !1, null, c, p), y.push(B))
                        }
                        if (e && !t) {
                            var L = o.tableBitmapSize.decode(v);
                            v.byteAlign();
                            var M = void 0;
                            if (0 === L) M = U(v, _, O); else {
                                var N = v.end, H = v.position + L;
                                v.end = H, M = W(v, _, O, !1), v.end = N, v.position = H
                            }
                            var q = g.length;
                            if (A === q - 1) y.push(M); else {
                                var $ = void 0, z = void 0, G = 0, J = void 0, K = void 0, X = void 0;
                                for ($ = A; $ < q; $++) {
                                    for (K = g[$], J = G + K, X = [], z = 0; z < O; z++) X.push(M[z].subarray(G, J));
                                    y.push(X), G = J
                                }
                            }
                        }
                    }
                    var Y = [], Q = [], Z = !1, ee = a.length + r;
                    while (Q.length < ee) {
                        var te = e ? m.decode(v) : n(k, "IAEX", j);
                        while (te--) Q.push(Z);
                        Z = !Z
                    }
                    for (var ne = 0, se = a.length; ne < se; ne++) Q[ne] && Y.push(a[ne]);
                    for (var ae = 0; ae < r; ne++, ae++) Q[ne] && Y.push(y[ae]);
                    return Y
                }

                function w(e, t, a, r, i, o, u, c, l, b, f, h, p, v, m, g, y, w, O) {
                    if (e && t) throw new x("refinement with Huffman is not supported");
                    var S, j, k = [];
                    for (S = 0; S < r; S++) {
                        if (j = new Uint8Array(a), i) for (var I = 0; I < a; I++) j[I] = i;
                        k.push(j)
                    }
                    var C = y.decoder, _ = y.contextCache, A = e ? -v.tableDeltaT.decode(O) : -n(_, "IADT", C), B = 0;
                    S = 0;
                    while (S < o) {
                        var D = e ? v.tableDeltaT.decode(O) : n(_, "IADT", C);
                        A += D;
                        var T = e ? v.tableFirstS.decode(O) : n(_, "IAFS", C);
                        B += T;
                        var P = B;
                        do {
                            var F = 0;
                            u > 1 && (F = e ? O.readBits(w) : n(_, "IAIT", C));
                            var R = u * A + F, E = e ? v.symbolIDTable.decode(O) : s(_, C, l),
                                L = t && (e ? O.readBit() : n(_, "IARI", C)), V = c[E], M = V[0].length, N = V.length;
                            if (L) {
                                var H = n(_, "IARDW", C), q = n(_, "IARDH", C), U = n(_, "IARDX", C), W = n(_, "IARDY", C);
                                M += H, N += q, V = d(M, N, m, V, (H >> 1) + U, (q >> 1) + W, !1, g, y)
                            }
                            var $, z, G, J = R - (1 & h ? 0 : N - 1), K = P - (2 & h ? M - 1 : 0);
                            if (b) {
                                for ($ = 0; $ < N; $++) if (j = k[K + $], j) {
                                    G = V[$];
                                    var X = Math.min(a - J, M);
                                    switch (p) {
                                        case 0:
                                            for (z = 0; z < X; z++) j[J + z] |= G[z];
                                            break;
                                        case 2:
                                            for (z = 0; z < X; z++) j[J + z] ^= G[z];
                                            break;
                                        default:
                                            throw new x("operator ".concat(p, " is not supported"))
                                    }
                                }
                                P += N - 1
                            } else {
                                for (z = 0; z < N; z++) if (j = k[J + z], j) switch (G = V[z], p) {
                                    case 0:
                                        for ($ = 0; $ < M; $++) j[K + $] |= G[$];
                                        break;
                                    case 2:
                                        for ($ = 0; $ < M; $++) j[K + $] ^= G[$];
                                        break;
                                    default:
                                        throw new x("operator ".concat(p, " is not supported"))
                                }
                                P += M - 1
                            }
                            S++;
                            var Y = e ? v.tableDeltaS.decode(O) : n(_, "IADS", C);
                            if (null === Y) break;
                            P += Y + f
                        } while (1)
                    }
                    return k
                }

                function O(e, t, n, s, a, r) {
                    var i = [];
                    e || (i.push({x: -t, y: 0}), 0 === a && (i.push({x: -3, y: -1}), i.push({x: 2, y: -2}), i.push({
                        x: -2,
                        y: -2
                    })));
                    for (var o = (s + 1) * t, u = l(e, o, n, a, !1, null, i, r), c = [], b = 0; b <= s; b++) {
                        for (var d = [], f = t * b, h = f + t, p = 0; p < n; p++) d.push(u[p].subarray(f, h));
                        c.push(d)
                    }
                    return c
                }

                function S(e, t, n, s, a, r, i, o, u, c, b, d, h, p, v) {
                    var m = null;
                    if (i) throw new x("skip is not supported");
                    if (0 !== o) throw new x("operator " + o + " is not supported in halftone region");
                    var g, y, w, O = [];
                    for (g = 0; g < a; g++) {
                        if (w = new Uint8Array(s), r) for (y = 0; y < s; y++) w[y] = r;
                        O.push(w)
                    }
                    var S = t.length, j = t[0], k = j[0].length, I = j.length, C = f(S), _ = [];
                    e || (_.push({x: n <= 1 ? 3 : 2, y: -1}), 0 === n && (_.push({x: -3, y: -1}), _.push({
                        x: 2,
                        y: -2
                    }), _.push({x: -2, y: -2})));
                    var A, B, D, T, P, F, R, E, L, V, N, H = [];
                    for (e && (A = new M(v.data, v.start, v.end)), g = C - 1; g >= 0; g--) B = e ? W(A, u, c, !0) : l(!1, u, c, n, !1, m, _, v), H[g] = B;
                    for (D = 0; D < c; D++) for (T = 0; T < u; T++) {
                        for (P = 0, F = 0, y = C - 1; y >= 0; y--) P = H[y][D][T] ^ P, F |= P << y;
                        if (R = t[F], E = b + D * p + T * h >> 8, L = d + D * h - T * p >> 8, E >= 0 && E + k <= s && L >= 0 && L + I <= a) for (g = 0; g < I; g++) for (N = O[L + g], V = R[g], y = 0; y < k; y++) N[E + y] |= V[y]; else {
                            var q = void 0, U = void 0;
                            for (g = 0; g < I; g++) if (U = L + g, !(U < 0 || U >= a)) for (N = O[U], V = R[g], y = 0; y < k; y++) q = E + y, q >= 0 && q < s && (N[q] |= V[y])
                        }
                    }
                    return O
                }

                function j(e, t) {
                    var n = {};
                    n.number = v(e, t);
                    var s = e[t + 4], r = 63 & s;
                    if (!a[r]) throw new x("invalid segment type: " + r);
                    n.type = r, n.typeName = a[r], n.deferredNonRetain = !!(128 & s);
                    var i = !!(64 & s), o = e[t + 5], u = o >> 5 & 7, c = [31 & o], l = t + 6;
                    if (7 === o) {
                        u = 536870911 & v(e, l - 1), l += 3;
                        var b = u + 7 >> 3;
                        c[0] = e[l++];
                        while (--b > 0) c.push(e[l++])
                    } else if (5 === o || 6 === o) throw new x("invalid referred-to flags");
                    n.retainBits = c;
                    var d = 4;
                    n.number <= 256 ? d = 1 : n.number <= 65536 && (d = 2);
                    var f, h, m = [];
                    for (f = 0; f < u; f++) {
                        var g = void 0;
                        g = 1 === d ? e[l] : 2 === d ? p(e, l) : v(e, l), m.push(g), l += d
                    }
                    if (n.referredTo = m, i ? (n.pageAssociation = v(e, l), l += 4) : n.pageAssociation = e[l++], n.length = v(e, l), l += 4, 4294967295 === n.length) {
                        if (38 !== r) throw new x("invalid unknown segment length");
                        var y = I(e, l), w = e[l + C], O = !!(1 & w), S = 6, j = new Uint8Array(S);
                        for (O || (j[0] = 255, j[1] = 172), j[2] = y.height >>> 24 & 255, j[3] = y.height >> 16 & 255, j[4] = y.height >> 8 & 255, j[5] = 255 & y.height, f = l, h = e.length; f < h; f++) {
                            var k = 0;
                            while (k < S && j[k] === e[f + k]) k++;
                            if (k === S) {
                                n.length = f + S;
                                break
                            }
                        }
                        if (4294967295 === n.length) throw new x("segment end was not found")
                    }
                    return n.headerEnd = l, n
                }

                function k(e, t, n, s) {
                    var a = [], r = n;
                    while (r < s) {
                        var i = j(t, r);
                        r = i.headerEnd;
                        var o = {header: i, data: t};
                        if (e.randomAccess || (o.start = r, r += i.length, o.end = r), a.push(o), 51 === i.type) break
                    }
                    if (e.randomAccess) for (var u = 0, c = a.length; u < c; u++) a[u].start = r, r += a[u].header.length, a[u].end = r;
                    return a
                }

                function I(e, t) {
                    return {
                        width: v(e, t),
                        height: v(e, t + 4),
                        x: v(e, t + 8),
                        y: v(e, t + 12),
                        combinationOperator: 7 & e[t + 16]
                    }
                }

                var C = 17;

                function _(e, t) {
                    var n, s, a, r, i = e.header, o = e.data, u = e.start, c = e.end;
                    switch (i.type) {
                        case 0:
                            var l = {}, b = p(o, u);
                            if (l.huffman = !!(1 & b), l.refinement = !!(2 & b), l.huffmanDHSelector = b >> 2 & 3, l.huffmanDWSelector = b >> 4 & 3, l.bitmapSizeSelector = b >> 6 & 1, l.aggregationInstancesSelector = b >> 7 & 1, l.bitmapCodingContextUsed = !!(256 & b), l.bitmapCodingContextRetained = !!(512 & b), l.template = b >> 10 & 3, l.refinementTemplate = b >> 12 & 1, u += 2, !l.huffman) {
                                for (r = 0 === l.template ? 4 : 1, s = [], a = 0; a < r; a++) s.push({
                                    x: h(o, u),
                                    y: h(o, u + 1)
                                }), u += 2;
                                l.at = s
                            }
                            if (l.refinement && !l.refinementTemplate) {
                                for (s = [], a = 0; a < 2; a++) s.push({x: h(o, u), y: h(o, u + 1)}), u += 2;
                                l.refinementAt = s
                            }
                            l.numberOfExportedSymbols = v(o, u), u += 4, l.numberOfNewSymbols = v(o, u), u += 4, n = [l, i.number, i.referredTo, o, u, c];
                            break;
                        case 6:
                        case 7:
                            var d = {};
                            d.info = I(o, u), u += C;
                            var f = p(o, u);
                            if (u += 2, d.huffman = !!(1 & f), d.refinement = !!(2 & f), d.logStripSize = f >> 2 & 3, d.stripSize = 1 << d.logStripSize, d.referenceCorner = f >> 4 & 3, d.transposed = !!(64 & f), d.combinationOperator = f >> 7 & 3, d.defaultPixelValue = f >> 9 & 1, d.dsOffset = f << 17 >> 27, d.refinementTemplate = f >> 15 & 1, d.huffman) {
                                var m = p(o, u);
                                u += 2, d.huffmanFS = 3 & m, d.huffmanDS = m >> 2 & 3, d.huffmanDT = m >> 4 & 3, d.huffmanRefinementDW = m >> 6 & 3, d.huffmanRefinementDH = m >> 8 & 3, d.huffmanRefinementDX = m >> 10 & 3, d.huffmanRefinementDY = m >> 12 & 3, d.huffmanRefinementSizeSelector = !!(16384 & m)
                            }
                            if (d.refinement && !d.refinementTemplate) {
                                for (s = [], a = 0; a < 2; a++) s.push({x: h(o, u), y: h(o, u + 1)}), u += 2;
                                d.refinementAt = s
                            }
                            d.numberOfSymbolInstances = v(o, u), u += 4, n = [d, i.referredTo, o, u, c];
                            break;
                        case 16:
                            var g = {}, y = o[u++];
                            g.mmr = !!(1 & y), g.template = y >> 1 & 3, g.patternWidth = o[u++], g.patternHeight = o[u++], g.maxPatternIndex = v(o, u), u += 4, n = [g, i.number, o, u, c];
                            break;
                        case 22:
                        case 23:
                            var w = {};
                            w.info = I(o, u), u += C;
                            var O = o[u++];
                            w.mmr = !!(1 & O), w.template = O >> 1 & 3, w.enableSkip = !!(8 & O), w.combinationOperator = O >> 4 & 7, w.defaultPixelValue = O >> 7 & 1, w.gridWidth = v(o, u), u += 4, w.gridHeight = v(o, u), u += 4, w.gridOffsetX = 4294967295 & v(o, u), u += 4, w.gridOffsetY = 4294967295 & v(o, u), u += 4, w.gridVectorX = p(o, u), u += 2, w.gridVectorY = p(o, u), u += 2, n = [w, i.referredTo, o, u, c];
                            break;
                        case 38:
                        case 39:
                            var S = {};
                            S.info = I(o, u), u += C;
                            var j = o[u++];
                            if (S.mmr = !!(1 & j), S.template = j >> 1 & 3, S.prediction = !!(8 & j), !S.mmr) {
                                for (r = 0 === S.template ? 4 : 1, s = [], a = 0; a < r; a++) s.push({
                                    x: h(o, u),
                                    y: h(o, u + 1)
                                }), u += 2;
                                S.at = s
                            }
                            n = [S, o, u, c];
                            break;
                        case 48:
                            var k = {
                                width: v(o, u),
                                height: v(o, u + 4),
                                resolutionX: v(o, u + 8),
                                resolutionY: v(o, u + 12)
                            };
                            4294967295 === k.height && delete k.height;
                            var _ = o[u + 16];
                            p(o, u + 17), k.lossless = !!(1 & _), k.refinement = !!(2 & _), k.defaultPixelValue = _ >> 2 & 1, k.combinationOperator = _ >> 3 & 3, k.requiresBuffer = !!(32 & _), k.combinationOperatorOverride = !!(64 & _), n = [k];
                            break;
                        case 49:
                            break;
                        case 50:
                            break;
                        case 51:
                            break;
                        case 53:
                            n = [i.number, o, u, c];
                            break;
                        case 62:
                            break;
                        default:
                            throw new x("segment type ".concat(i.typeName, "(").concat(i.type, ")") + " is not implemented")
                    }
                    var A = "on" + i.typeName;
                    A in t && t[A].apply(t, n)
                }

                function A(e, t) {
                    for (var n = 0, s = e.length; n < s; n++) _(e[n], t)
                }

                function B(e) {
                    for (var t = new T, n = 0, s = e.length; n < s; n++) {
                        var a = e[n], r = k({}, a.data, a.start, a.end);
                        A(r, t)
                    }
                    return t.buffer
                }

                function D(e) {
                    var t = e.length, n = 0;
                    if (151 !== e[n] || 74 !== e[n + 1] || 66 !== e[n + 2] || 50 !== e[n + 3] || 13 !== e[n + 4] || 10 !== e[n + 5] || 26 !== e[n + 6] || 10 !== e[n + 7]) throw new x("parseJbig2 - invalid header.");
                    var s = Object.create(null);
                    n += 8;
                    var a = e[n++];
                    s.randomAccess = !(1 & a), 2 & a || (s.numberOfPages = v(e, n), n += 4);
                    var r = k(s, e, n, t), i = new T;
                    A(r, i);
                    for (var o = i.currentPageInfo, u = o.width, c = o.height, l = i.buffer, b = new Uint8ClampedArray(u * c), d = 0, f = 0, h = 0; h < c; h++) for (var p = 0, m = void 0, g = 0; g < u; g++) p || (p = 128, m = l[f++]), b[d++] = m & p ? 0 : 255, p >>= 1;
                    return {imgData: b, width: u, height: c}
                }

                function T() {
                }

                function P(e) {
                    2 === e.length ? (this.isOOB = !0, this.rangeLow = 0, this.prefixLength = e[0], this.rangeLength = 0, this.prefixCode = e[1], this.isLowerRange = !1) : (this.isOOB = !1, this.rangeLow = e[0], this.prefixLength = e[1], this.rangeLength = e[2], this.prefixCode = e[3], this.isLowerRange = "lower" === e[4])
                }

                function F(e) {
                    this.children = [], e ? (this.isLeaf = !0, this.rangeLength = e.rangeLength, this.rangeLow = e.rangeLow, this.isLowerRange = e.isLowerRange, this.isOOB = e.isOOB) : this.isLeaf = !1
                }

                function R(e, t) {
                    t || this.assignPrefixCodes(e), this.rootNode = new F(null);
                    for (var n = 0, s = e.length; n < s; n++) {
                        var a = e[n];
                        a.prefixLength > 0 && this.rootNode.buildTree(a, a.prefixLength - 1)
                    }
                }

                function E(e, t, n) {
                    var s, a, r = e[t], i = 4294967295 & v(e, t + 1), o = 4294967295 & v(e, t + 5), u = new M(e, t + 9, n),
                        c = 1 + (r >> 1 & 7), l = 1 + (r >> 4 & 7), b = [], d = i;
                    do {
                        s = u.readBits(c), a = u.readBits(l), b.push(new P([d, s, a, 0])), d += 1 << a
                    } while (d < o);
                    return s = u.readBits(c), b.push(new P([i - 1, s, 32, 0, "lower"])), s = u.readBits(c), b.push(new P([o, s, 32, 0])), 1 & r && (s = u.readBits(c), b.push(new P([s, 0]))), new R(b, !1)
                }

                T.prototype = {
                    onPageInformation: function (e) {
                        this.currentPageInfo = e;
                        var t = e.width + 7 >> 3, n = new Uint8ClampedArray(t * e.height);
                        if (e.defaultPixelValue) for (var s = 0, a = n.length; s < a; s++) n[s] = 255;
                        this.buffer = n
                    }, drawBitmap: function (e, t) {
                        var n, s, a, r, i = this.currentPageInfo, o = e.width, u = e.height, c = i.width + 7 >> 3,
                            l = i.combinationOperatorOverride ? e.combinationOperator : i.combinationOperator,
                            b = this.buffer, d = 128 >> (7 & e.x), f = e.y * c + (e.x >> 3);
                        switch (l) {
                            case 0:
                                for (n = 0; n < u; n++) {
                                    for (a = d, r = f, s = 0; s < o; s++) t[n][s] && (b[r] |= a), a >>= 1, a || (a = 128, r++);
                                    f += c
                                }
                                break;
                            case 2:
                                for (n = 0; n < u; n++) {
                                    for (a = d, r = f, s = 0; s < o; s++) t[n][s] && (b[r] ^= a), a >>= 1, a || (a = 128, r++);
                                    f += c
                                }
                                break;
                            default:
                                throw new x("operator ".concat(l, " is not supported"))
                        }
                    }, onImmediateGenericRegion: function (e, n, s, a) {
                        var r = e.info, i = new t(n, s, a),
                            o = l(e.mmr, r.width, r.height, e.template, e.prediction, null, e.at, i);
                        this.drawBitmap(r, o)
                    }, onImmediateLosslessGenericRegion: function () {
                        this.onImmediateGenericRegion.apply(this, arguments)
                    }, onSymbolDictionary: function (e, n, s, a, r, i) {
                        var o, u;
                        e.huffman && (o = q(e, s, this.customTables), u = new M(a, r, i));
                        var c = this.symbols;
                        c || (this.symbols = c = {});
                        for (var l = [], b = 0, d = s.length; b < d; b++) {
                            var f = c[s[b]];
                            f && (l = l.concat(f))
                        }
                        var h = new t(a, r, i);
                        c[n] = m(e.huffman, e.refinement, l, e.numberOfNewSymbols, e.numberOfExportedSymbols, o, e.template, e.at, e.refinementTemplate, e.refinementAt, h, u)
                    }, onImmediateTextRegion: function (e, n, s, a, r) {
                        for (var i, o, u = e.info, c = this.symbols, l = [], b = 0, d = n.length; b < d; b++) {
                            var h = c[n[b]];
                            h && (l = l.concat(h))
                        }
                        var p = f(l.length);
                        e.huffman && (o = new M(s, a, r), i = H(e, n, this.customTables, l.length, o));
                        var v = new t(s, a, r),
                            m = w(e.huffman, e.refinement, u.width, u.height, e.defaultPixelValue, e.numberOfSymbolInstances, e.stripSize, l, p, e.transposed, e.dsOffset, e.referenceCorner, e.combinationOperator, i, e.refinementTemplate, e.refinementAt, v, e.logStripSize, o);
                        this.drawBitmap(u, m)
                    }, onImmediateLosslessTextRegion: function () {
                        this.onImmediateTextRegion.apply(this, arguments)
                    }, onPatternDictionary: function (e, n, s, a, r) {
                        var i = this.patterns;
                        i || (this.patterns = i = {});
                        var o = new t(s, a, r);
                        i[n] = O(e.mmr, e.patternWidth, e.patternHeight, e.maxPatternIndex, e.template, o)
                    }, onImmediateHalftoneRegion: function (e, n, s, a, r) {
                        var i = this.patterns[n[0]], o = e.info, u = new t(s, a, r),
                            c = S(e.mmr, i, e.template, o.width, o.height, e.defaultPixelValue, e.enableSkip, e.combinationOperator, e.gridWidth, e.gridHeight, e.gridOffsetX, e.gridOffsetY, e.gridVectorX, e.gridVectorY, u);
                        this.drawBitmap(o, c)
                    }, onImmediateLosslessHalftoneRegion: function () {
                        this.onImmediateHalftoneRegion.apply(this, arguments)
                    }, onTables: function (e, t, n, s) {
                        var a = this.customTables;
                        a || (this.customTables = a = {}), a[e] = E(t, n, s)
                    }
                }, F.prototype = {
                    buildTree: function (e, t) {
                        var n = e.prefixCode >> t & 1;
                        if (t <= 0) this.children[n] = new F(e); else {
                            var s = this.children[n];
                            s || (this.children[n] = s = new F(null)), s.buildTree(e, t - 1)
                        }
                    }, decodeNode: function (e) {
                        if (this.isLeaf) {
                            if (this.isOOB) return null;
                            var t = e.readBits(this.rangeLength);
                            return this.rangeLow + (this.isLowerRange ? -t : t)
                        }
                        var n = this.children[e.readBit()];
                        if (!n) throw new x("invalid Huffman data");
                        return n.decodeNode(e)
                    }
                }, R.prototype = {
                    decode: function (e) {
                        return this.rootNode.decodeNode(e)
                    }, assignPrefixCodes: function (e) {
                        for (var t = e.length, n = 0, s = 0; s < t; s++) n = Math.max(n, e[s].prefixLength);
                        for (var a = new Uint32Array(n + 1), r = 0; r < t; r++) a[e[r].prefixLength]++;
                        var i, o, u, c = 1, l = 0;
                        a[0] = 0;
                        while (c <= n) {
                            l = l + a[c - 1] << 1, i = l, o = 0;
                            while (o < t) u = e[o], u.prefixLength === c && (u.prefixCode = i, i++), o++;
                            c++
                        }
                    }
                };
                var L = {};

                function V(e) {
                    var t, n = L[e];
                    if (n) return n;
                    switch (e) {
                        case 1:
                            t = [[0, 1, 4, 0], [16, 2, 8, 2], [272, 3, 16, 6], [65808, 3, 32, 7]];
                            break;
                        case 2:
                            t = [[0, 1, 0, 0], [1, 2, 0, 2], [2, 3, 0, 6], [3, 4, 3, 14], [11, 5, 6, 30], [75, 6, 32, 62], [6, 63]];
                            break;
                        case 3:
                            t = [[-256, 8, 8, 254], [0, 1, 0, 0], [1, 2, 0, 2], [2, 3, 0, 6], [3, 4, 3, 14], [11, 5, 6, 30], [-257, 8, 32, 255, "lower"], [75, 7, 32, 126], [6, 62]];
                            break;
                        case 4:
                            t = [[1, 1, 0, 0], [2, 2, 0, 2], [3, 3, 0, 6], [4, 4, 3, 14], [12, 5, 6, 30], [76, 5, 32, 31]];
                            break;
                        case 5:
                            t = [[-255, 7, 8, 126], [1, 1, 0, 0], [2, 2, 0, 2], [3, 3, 0, 6], [4, 4, 3, 14], [12, 5, 6, 30], [-256, 7, 32, 127, "lower"], [76, 6, 32, 62]];
                            break;
                        case 6:
                            t = [[-2048, 5, 10, 28], [-1024, 4, 9, 8], [-512, 4, 8, 9], [-256, 4, 7, 10], [-128, 5, 6, 29], [-64, 5, 5, 30], [-32, 4, 5, 11], [0, 2, 7, 0], [128, 3, 7, 2], [256, 3, 8, 3], [512, 4, 9, 12], [1024, 4, 10, 13], [-2049, 6, 32, 62, "lower"], [2048, 6, 32, 63]];
                            break;
                        case 7:
                            t = [[-1024, 4, 9, 8], [-512, 3, 8, 0], [-256, 4, 7, 9], [-128, 5, 6, 26], [-64, 5, 5, 27], [-32, 4, 5, 10], [0, 4, 5, 11], [32, 5, 5, 28], [64, 5, 6, 29], [128, 4, 7, 12], [256, 3, 8, 1], [512, 3, 9, 2], [1024, 3, 10, 3], [-1025, 5, 32, 30, "lower"], [2048, 5, 32, 31]];
                            break;
                        case 8:
                            t = [[-15, 8, 3, 252], [-7, 9, 1, 508], [-5, 8, 1, 253], [-3, 9, 0, 509], [-2, 7, 0, 124], [-1, 4, 0, 10], [0, 2, 1, 0], [2, 5, 0, 26], [3, 6, 0, 58], [4, 3, 4, 4], [20, 6, 1, 59], [22, 4, 4, 11], [38, 4, 5, 12], [70, 5, 6, 27], [134, 5, 7, 28], [262, 6, 7, 60], [390, 7, 8, 125], [646, 6, 10, 61], [-16, 9, 32, 510, "lower"], [1670, 9, 32, 511], [2, 1]];
                            break;
                        case 9:
                            t = [[-31, 8, 4, 252], [-15, 9, 2, 508], [-11, 8, 2, 253], [-7, 9, 1, 509], [-5, 7, 1, 124], [-3, 4, 1, 10], [-1, 3, 1, 2], [1, 3, 1, 3], [3, 5, 1, 26], [5, 6, 1, 58], [7, 3, 5, 4], [39, 6, 2, 59], [43, 4, 5, 11], [75, 4, 6, 12], [139, 5, 7, 27], [267, 5, 8, 28], [523, 6, 8, 60], [779, 7, 9, 125], [1291, 6, 11, 61], [-32, 9, 32, 510, "lower"], [3339, 9, 32, 511], [2, 0]];
                            break;
                        case 10:
                            t = [[-21, 7, 4, 122], [-5, 8, 0, 252], [-4, 7, 0, 123], [-3, 5, 0, 24], [-2, 2, 2, 0], [2, 5, 0, 25], [3, 6, 0, 54], [4, 7, 0, 124], [5, 8, 0, 253], [6, 2, 6, 1], [70, 5, 5, 26], [102, 6, 5, 55], [134, 6, 6, 56], [198, 6, 7, 57], [326, 6, 8, 58], [582, 6, 9, 59], [1094, 6, 10, 60], [2118, 7, 11, 125], [-22, 8, 32, 254, "lower"], [4166, 8, 32, 255], [2, 2]];
                            break;
                        case 11:
                            t = [[1, 1, 0, 0], [2, 2, 1, 2], [4, 4, 0, 12], [5, 4, 1, 13], [7, 5, 1, 28], [9, 5, 2, 29], [13, 6, 2, 60], [17, 7, 2, 122], [21, 7, 3, 123], [29, 7, 4, 124], [45, 7, 5, 125], [77, 7, 6, 126], [141, 7, 32, 127]];
                            break;
                        case 12:
                            t = [[1, 1, 0, 0], [2, 2, 0, 2], [3, 3, 1, 6], [5, 5, 0, 28], [6, 5, 1, 29], [8, 6, 1, 60], [10, 7, 0, 122], [11, 7, 1, 123], [13, 7, 2, 124], [17, 7, 3, 125], [25, 7, 4, 126], [41, 8, 5, 254], [73, 8, 32, 255]];
                            break;
                        case 13:
                            t = [[1, 1, 0, 0], [2, 3, 0, 4], [3, 4, 0, 12], [4, 5, 0, 28], [5, 4, 1, 13], [7, 3, 3, 5], [15, 6, 1, 58], [17, 6, 2, 59], [21, 6, 3, 60], [29, 6, 4, 61], [45, 6, 5, 62], [77, 7, 6, 126], [141, 7, 32, 127]];
                            break;
                        case 14:
                            t = [[-2, 3, 0, 4], [-1, 3, 0, 5], [0, 1, 0, 0], [1, 3, 0, 6], [2, 3, 0, 7]];
                            break;
                        case 15:
                            t = [[-24, 7, 4, 124], [-8, 6, 2, 60], [-4, 5, 1, 28], [-2, 4, 0, 12], [-1, 3, 0, 4], [0, 1, 0, 0], [1, 3, 0, 5], [2, 4, 0, 13], [3, 5, 1, 29], [5, 6, 2, 61], [9, 7, 4, 125], [-25, 7, 32, 126, "lower"], [25, 7, 32, 127]];
                            break;
                        default:
                            throw new x("standard table B.".concat(e, " does not exist"))
                    }
                    for (var s = 0, a = t.length; s < a; s++) t[s] = new P(t[s]);
                    return n = new R(t, !0), L[e] = n, n
                }

                function M(e, t, n) {
                    this.data = e, this.start = t, this.end = n, this.position = t, this.shift = -1, this.currentByte = 0
                }

                function N(e, t, n) {
                    for (var s = 0, a = 0, r = t.length; a < r; a++) {
                        var i = n[t[a]];
                        if (i) {
                            if (e === s) return i;
                            s++
                        }
                    }
                    throw new x("can't find custom Huffman table")
                }

                function H(e, t, n, s, a) {
                    for (var r = [], i = 0; i <= 34; i++) {
                        var o = a.readBits(4);
                        r.push(new P([i, o, 0, 0]))
                    }
                    var u = new R(r, !1);
                    r.length = 0;
                    for (var c = 0; c < s;) {
                        var l = u.decode(a);
                        if (l >= 32) {
                            var b = void 0, d = void 0, f = void 0;
                            switch (l) {
                                case 32:
                                    if (0 === c) throw new x("no previous value in symbol ID table");
                                    d = a.readBits(2) + 3, b = r[c - 1].prefixLength;
                                    break;
                                case 33:
                                    d = a.readBits(3) + 3, b = 0;
                                    break;
                                case 34:
                                    d = a.readBits(7) + 11, b = 0;
                                    break;
                                default:
                                    throw new x("invalid code length in symbol ID table")
                            }
                            for (f = 0; f < d; f++) r.push(new P([c, b, 0, 0])), c++
                        } else r.push(new P([c, l, 0, 0])), c++
                    }
                    a.byteAlign();
                    var h, p, v, m = new R(r, !1), g = 0;
                    switch (e.huffmanFS) {
                        case 0:
                        case 1:
                            h = V(e.huffmanFS + 6);
                            break;
                        case 3:
                            h = N(g, t, n), g++;
                            break;
                        default:
                            throw new x("invalid Huffman FS selector")
                    }
                    switch (e.huffmanDS) {
                        case 0:
                        case 1:
                        case 2:
                            p = V(e.huffmanDS + 8);
                            break;
                        case 3:
                            p = N(g, t, n), g++;
                            break;
                        default:
                            throw new x("invalid Huffman DS selector")
                    }
                    switch (e.huffmanDT) {
                        case 0:
                        case 1:
                        case 2:
                            v = V(e.huffmanDT + 11);
                            break;
                        case 3:
                            v = N(g, t, n), g++;
                            break;
                        default:
                            throw new x("invalid Huffman DT selector")
                    }
                    if (e.refinement) throw new x("refinement with Huffman is not supported");
                    return {symbolIDTable: m, tableFirstS: h, tableDeltaS: p, tableDeltaT: v}
                }

                function q(e, t, n) {
                    var s, a, r, i, o = 0;
                    switch (e.huffmanDHSelector) {
                        case 0:
                        case 1:
                            s = V(e.huffmanDHSelector + 4);
                            break;
                        case 3:
                            s = N(o, t, n), o++;
                            break;
                        default:
                            throw new x("invalid Huffman DH selector")
                    }
                    switch (e.huffmanDWSelector) {
                        case 0:
                        case 1:
                            a = V(e.huffmanDWSelector + 2);
                            break;
                        case 3:
                            a = N(o, t, n), o++;
                            break;
                        default:
                            throw new x("invalid Huffman DW selector")
                    }
                    return e.bitmapSizeSelector ? (r = N(o, t, n), o++) : r = V(1), i = e.aggregationInstancesSelector ? N(o, t, n) : V(1), {
                        tableDeltaHeight: s,
                        tableDeltaWidth: a,
                        tableBitmapSize: r,
                        tableAggregateInstances: i
                    }
                }

                function U(e, t, n) {
                    for (var s = [], a = 0; a < n; a++) {
                        var r = new Uint8Array(t);
                        s.push(r);
                        for (var i = 0; i < t; i++) r[i] = e.readBit();
                        e.byteAlign()
                    }
                    return s
                }

                function W(e, t, n, s) {
                    for (var a, r = {
                        K: -1,
                        Columns: t,
                        Rows: n,
                        BlackIs1: !0,
                        EndOfBlock: s
                    }, i = new y(e, r), o = [], u = !1, c = 0; c < n; c++) {
                        var l = new Uint8Array(t);
                        o.push(l);
                        for (var b = -1, d = 0; d < t; d++) b < 0 && (a = i.readNextChar(), -1 === a && (a = 0, u = !0), b = 7), l[d] = a >> b & 1, b--
                    }
                    if (s && !u) for (var f = 5, h = 0; h < f; h++) if (-1 === i.readNextChar()) break;
                    return o
                }

                function $() {
                }

                return M.prototype = {
                    readBit: function () {
                        if (this.shift < 0) {
                            if (this.position >= this.end) throw new x("end of data while reading bit");
                            this.currentByte = this.data[this.position++], this.shift = 7
                        }
                        var e = this.currentByte >> this.shift & 1;
                        return this.shift--, e
                    }, readBits: function (e) {
                        var t, n = 0;
                        for (t = e - 1; t >= 0; t--) n |= this.readBit() << t;
                        return n
                    }, byteAlign: function () {
                        this.shift = -1
                    }, next: function () {
                        return this.position >= this.end ? -1 : this.data[this.position++]
                    }
                }, $.prototype = {
                    parseChunks: function (e) {
                        return B(e)
                    }, parse: function (e) {
                        var t = D(e), n = t.imgData, s = t.width, a = t.height;
                        return this.width = s, this.height = a, n
                    }
                }, $
            }()
    }, "7efc": function (e, t, n) {
        "use strict";
        n.d(t, "a", (function () {
            return r
        })), n.d(t, "b", (function () {
            return i
        })), n.d(t, "c", (function () {
            return o
        }));
        n("99af"), n("b680"), n("b64b"), n("d3b7"), n("ac1f"), n("5319"), n("1276"), n("8a59"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n("d139"), n("3a7b"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n("3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n("170b"), n("b39a"), n("72f7");
        var s = n("b85c"), a = n("6b33"), r = function (e, t, n) {
            var s, r = n[Object.keys(n)[0]]["json"]["ofd:Area"];
            if (r) {
                var i = r["ofd:PhysicalBox"];
                if (i) s = i; else {
                    var o = r["ofd:ApplicationBox"];
                    if (o) s = o; else {
                        var u = r["ofd:ContentBox"];
                        u && (s = u)
                    }
                }
            } else {
                var c = t["ofd:CommonData"]["ofd:PageArea"], l = c["ofd:PhysicalBox"];
                if (l) s = l; else {
                    var b = c["ofd:ApplicationBox"];
                    if (b) s = b; else {
                        var d = c["ofd:ContentBox"];
                        d && (s = d)
                    }
                }
            }
            var f = s.split(" "), h = ((e - 10) / parseFloat(f[2])).toFixed(1);
            Object(a["n"])(h + 1);
            var p = h - 1.7;
            return Object(a["o"])(p), s = Object(a["l"])(s), s = Object(a["e"])(s), s
        }, i = function (e, t) {
            var n, s = t[Object.keys(t)[0]]["json"]["ofd:Area"];
            if (s) {
                var r = s["ofd:PhysicalBox"];
                if (r) n = r; else {
                    var i = s["ofd:ApplicationBox"];
                    if (i) n = i; else {
                        var o = s["ofd:ContentBox"];
                        o && (n = o)
                    }
                }
            } else {
                var u = e["ofd:CommonData"]["ofd:PageArea"], c = u["ofd:PhysicalBox"];
                if (c) n = c; else {
                    var l = u["ofd:ApplicationBox"];
                    if (l) n = l; else {
                        var b = u["ofd:ContentBox"];
                        b && (n = b)
                    }
                }
            }
            return n = Object(a["l"])(n), n = Object(a["e"])(n), n
        }, o = function (e, t, n, r, i, o, b) {
            var d = Object.keys(t)[0], h = t[d]["json"]["ofd:Template"];
            if (h) {
                var p = [], v = n[h["@_TemplateID"]]["json"]["ofd:Content"]["ofd:Layer"];
                p = p.concat(v);
                var m, g = Object(s["a"])(p);
                try {
                    for (g.s(); !(m = g.n()).done;) {
                        var y = m.value;
                        y && l(e, r, i, o, b, y, !1)
                    }
                } catch (E) {
                    g.e(E)
                } finally {
                    g.f()
                }
            }
            var x = t[d]["json"]["ofd:Content"]["ofd:Layer"], w = [];
            w = w.concat(x);
            var O, S = Object(s["a"])(w);
            try {
                for (S.s(); !(O = S.n()).done;) {
                    var j = O.value;
                    j && l(e, r, i, o, b, j, !1)
                }
            } catch (E) {
                S.e(E)
            } finally {
                S.f()
            }
            if (t[d].stamp) {
                var k, I = t[d].json.pfIndex, C = Object(s["a"])(t[d].stamp);
                try {
                    for (C.s(); !(k = C.n()).done;) {
                        var _ = k.value;
                        if ("ofd" === _.type) c(e, _.obj.pages, _.obj.tpls, !0, _.stamp.stampAnnot, _.obj.fontResObj, _.obj.drawParamResObj, _.obj.multiMediaResObj, b, _.stamp.sealObj.SES_Signature, _.stamp.signedInfo, I); else {
                            var A = Object(a["e"])(_.obj.boundary),
                                B = (Array.isArray(_.stamp.stampAnnot) ? _.stamp.stampAnnot[0]["@_ID"] : _.stamp.stampAnnot["@_ID"]) + I,
                                D = f(e.style.width, e.style.height, _.obj.img, A, _.obj.clip, !0, _.stamp.sealObj.SES_Signature, _.stamp.signedInfo, B);
                            e.appendChild(D)
                        }
                    }
                } catch (E) {
                    C.e(E)
                } finally {
                    C.f()
                }
            }
            if (t[d].annotation) {
                var T, P = t[d].json.pfIndex, F = Object(s["a"])(t[d].annotation);
                try {
                    for (F.s(); !(T = F.n()).done;) {
                        var R = T.value;
                        u(e, R, r, i, o, b, P)
                    }
                } catch (E) {
                    F.e(E)
                } finally {
                    F.f()
                }
            }
        }, u = function (e, t, n, s, r, i, o) {
            var u = document.createElement("div");
            u.setAttribute("style", "overflow: hidden;z-index:".concat(t["@_ID"] + o, ";position:relative;"));
            var c = t["appearance"]["@_Boundary"];
            if (c) {
                var b = Object(a["e"])(Object(a["l"])(c));
                u.setAttribute("style", "overflow: hidden;z-index:".concat(t["@_ID"] + o, ";position:absolute; left: ").concat(b.x, "px; top: ").concat(b.y, "px; width: ").concat(b.w, "px; height: ").concat(b.h, "px"))
            }
            var d = t["appearance"];
            l(u, n, s, r, i, d, !1), e.appendChild(u)
        }, c = function (e, t, n, r, i, o, u, c, b, d, f, h) {
            var p, v = Object(s["a"])(t);
            try {
                for (v.s(); !(p = v.n()).done;) {
                    var m = p.value, g = Object.keys(m)[0], y = {x: 0, y: 0, w: 0, h: 0};
                    r && i && (y = i.boundary);
                    var x = Object(a["e"])(y), w = document.createElement("div");
                    w.setAttribute("name", "seal_img_div"), w.setAttribute("style", "z-index:".concat(h + 1e4, ";cursor: pointer; position:relative; left: ").concat(x.x, "px; top: ").concat(x.y, "px; width: ").concat(x.w, "px; height: ").concat(x.h, "px")), w.setAttribute("data-ses-signature", "".concat(JSON.stringify(d))), w.setAttribute("data-signed-info", "".concat(JSON.stringify(f)));
                    var O = m[g]["json"]["ofd:Template"];
                    if (O) {
                        var S = n[O["@_TemplateID"]]["json"]["ofd:Content"]["ofd:Layer"], j = [];
                        j = j.concat(S);
                        var k, I = Object(s["a"])(j);
                        try {
                            for (I.s(); !(k = I.n()).done;) {
                                var C = k.value;
                                C && l(w, o, u, c, b, C, r)
                            }
                        } catch (P) {
                            I.e(P)
                        } finally {
                            I.f()
                        }
                    }
                    var _ = m[g]["json"]["ofd:Content"]["ofd:Layer"], A = [];
                    A = A.concat(_);
                    var B, D = Object(s["a"])(A);
                    try {
                        for (D.s(); !(B = D.n()).done;) {
                            var T = B.value;
                            T && l(w, o, u, c, b, T, r)
                        }
                    } catch (P) {
                        D.e(P)
                    } finally {
                        D.f()
                    }
                    e.appendChild(w)
                }
            } catch (P) {
                v.e(P)
            } finally {
                v.f()
            }
        }, l = function e(t, n, r, i, o, u, c, l, d, f) {
            var v = null, m = null, g = Object(a["f"])(.353), y = u["@_DrawParam"];
            y && Object.keys(r).length > 0 && r[y] && (r[y]["relative"] && (y = r[y]["relative"], r[y]["FillColor"] && (v = Object(a["j"])(r[y]["FillColor"])), r[y]["StrokeColor"] && (m = Object(a["j"])(r[y]["StrokeColor"])), r[y]["LineWidth"] && (g = Object(a["f"])(r[y]["LineWidth"]))), r[y]["FillColor"] && (v = Object(a["j"])(r[y]["FillColor"])), r[y]["StrokeColor"] && (m = Object(a["j"])(r[y]["StrokeColor"])), r[y]["LineWidth"] && (g = Object(a["f"])(r[y]["LineWidth"])));
            var x = u["ofd:ImageObject"], w = [];
            w = w.concat(x);
            var O, S = Object(s["a"])(w);
            try {
                for (S.s(); !(O = S.n()).done;) {
                    var j = O.value;
                    if (j) {
                        var k = b(t.style.width, t.style.height, i, j, c, d);
                        t.appendChild(k)
                    }
                }
            } catch (ee) {
                S.e(ee)
            } finally {
                S.f()
            }
            var I = u["ofd:PathObject"], C = [];
            C = C.concat(I);
            var _, A = Object(s["a"])(C);
            try {
                for (A.s(); !(_ = A.n()).done;) {
                    var B = _.value;
                    if (B) {
                        var D = p(r, B, v, m, g, c, l, d, f);
                        t.appendChild(D)
                    }
                }
            } catch (ee) {
                A.e(ee)
            } finally {
                A.f()
            }
            var T = u["ofd:TextObject"], P = [];
            P = P.concat(T);
            var F, R = Object(s["a"])(P);
            try {
                for (R.s(); !(F = R.n()).done;) {
                    var E = F.value;
                    if (E) {
                        var L = h(n, E, v, m);
                        t.appendChild(L)
                    }
                }
            } catch (ee) {
                R.e(ee)
            } finally {
                R.f()
            }
            var V = u["ofd:CompositeObject"], M = [];
            M = M.concat(V);
            var N, H = Object(s["a"])(M);
            try {
                for (H.s(); !(N = H.n()).done;) {
                    var q = N.value;
                    if (q) {
                        var U, W = Object(s["a"])(o);
                        try {
                            for (W.s(); !(U = W.n()).done;) {
                                var $ = U.value;
                                if ($["@_ID"] === q["@_ResourceID"]) {
                                    var z = q["@_Alpha"], G = q["@_Boundary"], J = q["@_CTM"];
                                    e(t, n, r, i, o, $["ofd:Content"], !1, z, G, J);
                                    break
                                }
                            }
                        } catch (ee) {
                            W.e(ee)
                        } finally {
                            W.f()
                        }
                    }
                }
            } catch (ee) {
                H.e(ee)
            } finally {
                H.f()
            }
            var K = u["ofd:PageBlock"], X = [];
            X = X.concat(K);
            var Y, Q = Object(s["a"])(X);
            try {
                for (Q.s(); !(Y = Q.n()).done;) {
                    var Z = Y.value;
                    Z && e(t, n, r, i, o, Z, c)
                }
            } catch (ee) {
                Q.e(ee)
            } finally {
                Q.f()
            }
        }, b = function (e, t, n, s, r, i) {
            var o = Object(a["l"])(s["@_Boundary"]);
            o = Object(a["e"])(o);
            var u = s["@_ResourceID"];
            if ("gbig2" === n[u].format) {
                var c = n[u].img, l = n[u].width, b = n[u].height;
                return d(c, l, b, o, s["@_ID"])
            }
            var h = s["@_CTM"];
            return f(e, t, n[u].img, o, !1, r, null, null, s["@_ID"], h, i)
        }, d = function (e, t, n, s, a) {
            for (var r = new Uint8ClampedArray(4 * t * n), i = 0; i < e.length; i++) r[4 * i] = e[i], r[4 * i + 1] = e[i], r[4 * i + 2] = e[i], r[4 * i + 3] = 255;
            var o = new ImageData(r, t, n), u = document.createElement("canvas");
            u.width = t, u.height = n;
            var c = u.getContext("2d");
            return c.putImageData(o, 0, 0), u.setAttribute("style", "left: ".concat(s.x, "px; top: ").concat(s.y, "px; width: ").concat(s.w, "px; height: ").concat(s.h, "px;z-index: ").concat(a)), u.style.position = "absolute", u
        }, f = function (e, t, n, s, r, i, o, u, c, l, b) {
            var d = document.createElement("div");
            i && (d.setAttribute("name", "seal_img_div"), d.setAttribute("data-ses-signature", "".concat(JSON.stringify(o))), d.setAttribute("data-signed-info", "".concat(JSON.stringify(u))));
            var f = document.createElement("img");
            if (f.src = n, i && (f.setAttribute("width", "100%"), f.setAttribute("height", "100%")), l) {
                var h = Object(a["k"])(l);
                f.setAttribute("width", "".concat(Object(a["f"])(h[0]), "px")), f.setAttribute("height", "".concat(Object(a["f"])(h[3]), "px")), f.setAttribute("transform", "matrix(".concat(h[0], " ").concat(h[1], " ").concat(h[2], " ").concat(h[3], " ").concat(Object(a["f"])(h[4]), " ").concat(Object(a["f"])(h[5]), ")"))
            }
            b && (f.setAttribute("width", "100%"), f.setAttribute("height", "100%"), f.removeAttribute("transform")), d.appendChild(f);
            var p = parseFloat(e.replace("px", "")), v = parseFloat(t.replace("px", "")), m = s.w > p ? p : s.w,
                g = s.h > v ? v : s.h, y = "";
            return r && (r = Object(a["e"])(r), y = "clip: rect(".concat(r.y, "px, ").concat(r.w + r.x, "px, ").concat(r.h + r.y, "px, ").concat(r.x, "px)")), d.setAttribute("style", "cursor: pointer; overflow: hidden; position: absolute; left: ".concat(y ? s.x : s.x < 0 ? 0 : s.x, "px; top: ").concat(y ? s.y : s.y < 0 ? 0 : s.y, "px; width: ").concat(m, "px; height: ").concat(g, "px; ").concat(y, ";z-index: ").concat(c)), d
        }, h = function (e, t, n, r) {
            var i = 1, o = Object(a["l"])(t["@_Boundary"]);
            o = Object(a["e"])(o);
            var u = t["@_CTM"], c = t["@_HScale"], l = t["@_Font"], b = t["@_Weight"],
                d = Object(a["f"])(parseFloat(t["@_Size"])), f = [];
            f = f.concat(t["ofd:TextCode"]);
            var h = Object(a["c"])(f), p = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            p.setAttribute("version", "1.1");
            var v = t["ofd:FillColor"], m = !1;
            if (v) {
                v["@_Value"] && (n = Object(a["j"])(v["@_Value"]));
                var g = v["@_Alpha"];
                g && (i = g > 1 ? g / 255 : g);
                var y = v["ofd:AxialShd"];
                if (y) {
                    m = !0;
                    var x = document.createElement("linearGradient");
                    x.setAttribute("id", "".concat(t["@_ID"])), x.setAttribute("x1", "0%"), x.setAttribute("y1", "0%"), x.setAttribute("x2", "100%"), x.setAttribute("y2", "100%");
                    var w, O = Object(s["a"])(y["ofd:Segment"]);
                    try {
                        for (O.s(); !(w = O.n()).done;) {
                            var S = w.value;
                            if (S) {
                                var j = document.createElement("stop");
                                j.setAttribute("offset", "".concat(100 * S["@_Position"], "%")), j.setAttribute("style", "stop-color:".concat(Object(a["j"])(S["ofd:Color"]["@_Value"]), ";stop-opacity:1")), x.appendChild(j), n = Object(a["j"])(S["ofd:Color"]["@_Value"])
                            }
                        }
                    } catch (F) {
                        O.e(F)
                    } finally {
                        O.f()
                    }
                    p.appendChild(x)
                }
            }
            var k, I = Object(s["a"])(h);
            try {
                for (I.s(); !(k = I.n()).done;) {
                    var C = k.value;
                    if (C && !isNaN(C.x)) {
                        var _ = document.createElementNS("http://www.w3.org/2000/svg", "text");
                        if (_.setAttribute("x", C.x), _.setAttribute("y", C.y), _.innerHTML = C.text, u) {
                            var A = Object(a["k"])(u);
                            _.setAttribute("transform", "matrix(".concat(A[0], " ").concat(A[1], " ").concat(A[2], " ").concat(A[3], " ").concat(Object(a["f"])(A[4]), " ").concat(Object(a["f"])(A[5]), ")"))
                        }
                        c && _.setAttribute("transform", "matrix(".concat(c, ", 0, 0, 1, ").concat((1 - c) * C.x, ", 0)")), m ? _.setAttribute("fill", n) : (_.setAttribute("fill", r), _.setAttribute("fill", n), _.setAttribute("fill-opacity", i)), _.setAttribute("style", "font-weight: ".concat(b, ";font-size:").concat(d, "px;font-family: ").concat(Object(a["h"])(e[l]), ";")), p.appendChild(_)
                    }
                }
            } catch (F) {
                I.e(F)
            } finally {
                I.f()
            }
            var B = o.w, D = o.h, T = o.x, P = o.y;
            return p.setAttribute("style", "overflow:visible;position:absolute;width:".concat(B, "px;height:").concat(D, "px;left:").concat(T, "px;top:").concat(P, "px;z-index:").concat(t["@_ID"])), p
        }, p = function (e, t, n, r, i, o, u, c, l) {
            var b = document.createElementNS("http://www.w3.org/2000/svg", "svg");
            b.setAttribute("version", "1.1");
            var d = Object(a["l"])(t["@_Boundary"]);
            if (!d) return b;
            d = Object(a["e"])(d);
            var f = t["@_LineWidth"], h = t["ofd:AbbreviatedData"], p = Object(a["b"])(Object(a["d"])(h)),
                v = t["@_CTM"], m = document.createElementNS("http://www.w3.org/2000/svg", "path");
            f && (i = Object(a["f"])(f));
            var g = t["@_DrawParam"];
            if (g && (f = e[g].LineWidth, f && (i = Object(a["f"])(f))), v) {
                var y = Object(a["k"])(v);
                m.setAttribute("transform", "matrix(".concat(y[0], " ").concat(y[1], " ").concat(y[2], " ").concat(y[3], " ").concat(Object(a["f"])(y[4]), " ").concat(Object(a["f"])(y[5]), ")"))
            }
            var x = t["ofd:StrokeColor"];
            if (x) {
                x["@_Value"] && (r = Object(a["j"])(x["@_Value"]));
                var w = x["ofd:AxialShd"];
                if (w) {
                    !0;
                    var O = document.createElement("linearGradient");
                    O.setAttribute("id", "".concat(t["@_ID"])), O.setAttribute("x1", "0%"), O.setAttribute("y1", "0%"), O.setAttribute("x2", "100%"), O.setAttribute("y2", "100%");
                    var S, j = Object(s["a"])(w["ofd:Segment"]);
                    try {
                        for (j.s(); !(S = j.n()).done;) {
                            var k = S.value;
                            if (k) {
                                var I = document.createElement("stop");
                                I.setAttribute("offset", "".concat(100 * k["@_Position"], "%")), I.setAttribute("style", "stop-color:".concat(Object(a["j"])(k["ofd:Color"]["@_Value"]), ";stop-opacity:1")), O.appendChild(I), r = Object(a["j"])(k["ofd:Color"]["@_Value"])
                            }
                        }
                    } catch (Q) {
                        j.e(Q)
                    } finally {
                        j.f()
                    }
                    b.appendChild(O)
                }
            }
            var C = t["ofd:FillColor"];
            if (C) {
                C["@_Value"] && (n = Object(a["j"])(C["@_Value"]));
                var _ = C["ofd:AxialShd"];
                if (_) {
                    !0;
                    var A = document.createElement("linearGradient");
                    A.setAttribute("id", "".concat(t["@_ID"])), A.setAttribute("x1", "0%"), A.setAttribute("y1", "0%"), A.setAttribute("x2", "100%"), A.setAttribute("y2", "100%");
                    var B, D = Object(s["a"])(_["ofd:Segment"]);
                    try {
                        for (D.s(); !(B = D.n()).done;) {
                            var T = B.value;
                            if (T) {
                                var P = document.createElement("stop");
                                P.setAttribute("offset", "".concat(100 * T["@_Position"], "%")), P.setAttribute("style", "stop-color:".concat(Object(a["j"])(T["ofd:Color"]["@_Value"]), ";stop-opacity:1")), A.appendChild(P), n = Object(a["j"])(T["ofd:Color"]["@_Value"])
                            }
                        }
                    } catch (Q) {
                        D.e(Q)
                    } finally {
                        D.f()
                    }
                    b.appendChild(A)
                }
            }
            if (i > 0 && !r && (r = n, r || (r = "rgb(0, 0, 0)")), u && m.setAttribute("fill-opacity", "".concat(u / 255)), "false" != t["@_Stroke"] && (m.setAttribute("stroke", "".concat(r)), m.setAttribute("stroke-width", "".concat(i, "px"))), "false" != t["@_Fill"] && m.setAttribute("fill", "".concat(o ? "none" : n || "none")), t["@_Join"] && m.setAttribute("stroke-linejoin", "".concat(t["@_Join"])), t["@_Cap"] && m.setAttribute("stroke-linecap", "".concat(t["@_Cap"])), t["@_DashPattern"]) {
                var F = t["@_DashPattern"], R = Object(a["k"])(F), E = 0;
                t["@_DashOffset"] && (E = t["@_DashOffset"]), m.setAttribute("stroke-dasharray", "".concat(Object(a["f"])(R[0]), ",").concat(Object(a["f"])(R[1]))), m.setAttribute("stroke-dashoffset", "".concat(Object(a["f"])(E), "px"))
            }
            var L, V = "", M = Object(s["a"])(p);
            try {
                for (M.s(); !(L = M.n()).done;) {
                    var N = L.value;
                    "M" === N.type ? V += "M".concat(N.x, " ").concat(N.y, " ") : "L" === N.type ? V += "L".concat(N.x, " ").concat(N.y, " ") : "B" === N.type ? V += "C".concat(N.x1, " ").concat(N.y1, " ").concat(N.x2, " ").concat(N.y2, " ").concat(N.x3, " ").concat(N.y3, " ") : "Q" === N.type ? V += "Q".concat(N.x1, " ").concat(N.y1, " ").concat(N.x2, " ").concat(N.y2, " ") : "A" === N.type ? V += "A".concat(N.rx, ",").concat(N.ry, " ").concat(N.rotation, " ").concat(N.arc, ",").concat(N.sweep, " ").concat(N.x, ",").concat(N.y) : "C" === N.type && (V += "Z")
                }
            } catch (Q) {
                M.e(Q)
            } finally {
                M.f()
            }
            m.setAttribute("d", V), b.appendChild(m);
            var H = o ? d.w : Math.ceil(d.w), q = o ? d.h : Math.ceil(d.h), U = d.x, W = d.y;
            if (b.setAttribute("style", "overflow:visible;position:absolute;width:".concat(H, "px;height:").concat(q, "px;left:").concat(U, "px;top:").concat(W, "px;z-index:").concat(t["@_ID"])), c) {
                var $ = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                $.setAttribute("version", "1.1");
                var z = Object(a["l"])(c);
                z = Object(a["e"])(z);
                var G = Math.ceil(z.w), J = Math.ceil(z.h), K = z.x, X = z.y;
                if ($.setAttribute("style", "overflow:hidden;position:absolute;width:".concat(G, "px;height:").concat(J, "px;left:").concat(K, "px;top:").concat(X, "px;z-index:").concat(t["@_ID"])), l) {
                    var Y = Object(a["k"])(l);
                    b.setAttribute("transform", "matrix(".concat(Y[0], " ").concat(Y[1], " ").concat(Y[2], " ").concat(Y[3], " ").concat(Object(a["f"])(Y[4]), " ").concat(Object(a["f"])(Y[5]), ")"))
                }
                return $.appendChild(b), $
            }
            return b
        }
    }, "7f3b": function (e, t, n) {
        "use strict";
        (function (e) {
            n("a4d3"), n("e01a"), n("a630"), n("caad"), n("fb6a"), n("6c57"), n("4ec9"), n("90d7"), n("a9e3"), n("8ba4"), n("9129"), n("4fad"), n("c1f9"), n("d3b7"), n("07ac"), n("25f0"), n("6062"), n("f5b2"), n("8a79"), n("f6d6"), n("2532"), n("3ca3"), n("843c"), n("4d90"), n("2ca0"), n("5cc6"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n("d139"), n("3a7b"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n("3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n("170b"), n("b39a"), n("72f7"), n("10d1"), n("1fe2"), n("ddb0"), n("2b3d");
            var t = n("d00a");
            "undefined" !== typeof PDFJSDev && PDFJSDev.test("SKIP_BABEL") || "undefined" !== typeof globalThis && globalThis._pdfjsCompatibilityChecked || ("undefined" !== typeof globalThis && globalThis.Math === Math || (globalThis = n("eb73")), globalThis._pdfjsCompatibilityChecked = !0, function () {
                !globalThis.btoa && t["a"] && (globalThis.btoa = function (t) {
                    return e.from(t, "binary").toString("base64")
                })
            }(), function () {
                !globalThis.atob && t["a"] && (globalThis.atob = function (t) {
                    return e.from(t, "base64").toString("binary")
                })
            }(), function () {
                String.prototype.startsWith || n("d2a2")
            }(), function () {
                String.prototype.endsWith || n("8f4c")
            }(), function () {
                String.prototype.includes || n("4661")
            }(), function () {
                Array.prototype.includes || n("bf2c")
            }(), function () {
                Array.from || n("6b84")
            }(), function () {
                Object.assign || n("2418")
            }(), function () {
                Object.fromEntries || n("8ac5")
            }(), function () {
                Math.log2 || (Math.log2 = n("dc57"))
            }(), function () {
                Number.isNaN || (Number.isNaN = n("9020"))
            }(), function () {
                Number.isInteger || (Number.isInteger = n("f2e6"))
            }(), function () {
                Uint8Array.prototype.slice || n("8f2a")
            }(), function () {
                "undefined" !== typeof PDFJSDev && PDFJSDev.test("IMAGE_DECODERS") || globalThis.Promise.allSettled || (globalThis.Promise = n("3980"))
            }(), function () {
                "undefined" !== typeof PDFJSDev && PDFJSDev.test("PRODUCTION") && PDFJSDev.test("GENERIC") && (PDFJSDev.test("IMAGE_DECODERS") || (globalThis.URL = n("14d8")))
            }(), function () {
                if ("undefined" === typeof PDFJSDev || !PDFJSDev.test("IMAGE_DECODERS")) {
                    var e = !1;
                    if ("undefined" !== typeof ReadableStream) try {
                        new ReadableStream({
                            start: function (e) {
                                e.close()
                            }
                        }), e = !0
                    } catch (t) {
                    }
                    e || (globalThis.ReadableStream = n("87c2").ReadableStream)
                }
            }(), function () {
                globalThis.Map && globalThis.Map.prototype.entries || (globalThis.Map = n("5eff"))
            }(), function () {
                globalThis.Set && globalThis.Set.prototype.entries || (globalThis.Set = n("9a35"))
            }(), function () {
                globalThis.WeakMap || (globalThis.WeakMap = n("ad63"))
            }(), function () {
                globalThis.WeakSet || (globalThis.WeakSet = n("ee42"))
            }(), function () {
                String.prototype.codePointAt || n("d627")
            }(), function () {
                String.fromCodePoint || (String.fromCodePoint = n("1cd7"))
            }(), function () {
                globalThis.Symbol || n("1f4a")
            }(), function () {
                String.prototype.padStart || n("1920")
            }(), function () {
                String.prototype.padEnd || n("476b")
            }(), function () {
                Object.values || (Object.values = n("4e28"))
            }(), function () {
                Object.entries || (Object.entries = n("a960"))
            }())
        }).call(this, n("b639").Buffer)
    }, "81a2": function (e, t, n) {
        "use strict";
        (function (e) {
            n.d(t, "c", (function () {
                return l
            })), n.d(t, "d", (function () {
                return d
            })), n.d(t, "e", (function () {
                return f
            })), n.d(t, "a", (function () {
                return h
            })), n.d(t, "f", (function () {
                return p
            })), n.d(t, "b", (function () {
                return v
            }));
            n("99af"), n("c19f"), n("b64b"), n("d3b7"), n("96cf");
            var s = n("1da1"), a = n("7efc"), r = n("3662"), i = n("67d3"), o = n("a9c6"), u = n("6b33"), c = n("0083"),
                l = function (e) {
                    e.ofd instanceof File || e.ofd instanceof ArrayBuffer ? b(e) : c["getBinaryContent"](e.ofd, (function (t, n) {
                        t ? e.fail && e.fail(t) : (e.ofd = n, b(e))
                    }))
                }, b = function (t) {
                    e.xmlParseFlag = 0, r["a"].call(this, Object(s["a"])(regeneratorRuntime.mark((function e() {
                        return regeneratorRuntime.wrap((function (e) {
                            while (1) switch (e.prev = e.next) {
                                case 0:
                                    return e.next = 2, Object(i["c"])(t.ofd);
                                case 2:
                                    return e.abrupt("return", e.sent);
                                case 3:
                                case"end":
                                    return e.stop()
                            }
                        }), e)
                    }))), i["a"], i["b"]).then((function (e) {
                        t.success && t.success(e)
                    })).catch((function (e) {
                        console.log(e), t.fail && t.fail(e)
                    }))
                }, d = function (e, t) {
                    var n = [];
                    if (!t) return n;
                    for (var s = 0; s < t.pages.length; s++) {
                        var r = t.pages[s], i = Object(a["a"])(e, t.document, r), o = Object.keys(r)[0],
                            u = document.createElement("div");
                        u.id = o, s === t.pages.length - 1 ? u.setAttribute("style", "position: relative;width:".concat(i.w, "px;height:").concat(i.h, "px;background: white;")) : u.setAttribute("style", "margin-bottom: 20px;position: relative;width:".concat(i.w, "px;height:").concat(i.h, "px;background: white;")), Object(a["c"])(u, r, t.tpls, t.fontResObj, t.drawParamResObj, t.multiMediaResObj, t.compositeGraphicUnits), n.push(u)
                    }
                    return n
                }, f = function (e) {
                    var t = [];
                    if (!e) return t;
                    for (var n = 0; n < e.pages.length; n++) {
                        var s = e.pages[n], r = Object(a["b"])(e.document, s), i = Object.keys(s)[0],
                            o = document.createElement("div");
                        o.id = i, n === e.pages.length - 1 ? o.setAttribute("style", "overflow: hidden;position: relative;width:".concat(r.w, "px;height:").concat(r.h, "px;background: white;")) : o.setAttribute("style", "overflow: hidden;margin-bottom: 20px;position: relative;width:".concat(r.w, "px;height:").concat(r.h, "px;background: white;")), Object(a["c"])(o, s, e.tpls, e.fontResObj, e.drawParamResObj, e.multiMediaResObj, e.compositeGraphicUnits), t.push(o)
                    }
                    return t
                }, h = function (e) {
                    return Object(o["a"])(e)
                }, p = function (e) {
                    Object(u["o"])(e)
                }, v = function () {
                    return Object(u["i"])()
                }
        }).call(this, n("c8ba"))
    }, 8374: function (e, t, n) {
        "use strict";
        (function (e) {
            n("c975"), n("c19f"), n("b0c0"), n("b64b"), n("d3b7"), n("ac1f"), n("3ca3"), n("5319"), n("841c"), n("1276"), n("5cc6"), n("9a8c"), n("a975"), n("735e"), n("c1ac"), n("d139"), n("3a7b"), n("d5d6"), n("82f8"), n("e91f"), n("60bd"), n("5f96"), n("3280"), n("3fcc"), n("ca91"), n("25a1"), n("cd26"), n("3c5d"), n("2954"), n("649e"), n("219c"), n("170b"), n("b39a"), n("72f7"), n("ddb0"), n("2b3d");
            var s = n("b85c"), a = n("81a2"), r = n("0083");
            t["a"] = {
                name: "HelloWorld", data: function () {
                    return {
                        pdfFile: null,
                        ofdBase64: null,
                        loading: !1,
                        pageIndex: 1,
                        pageCount: 0,
                        scale: 0,
                        title: null,
                        value: null,
                        dialogFormVisible: !1,
                        ofdObj: null,
                        screenWidth: document.body.clientWidth
                    }
                }, created: function () {
                    this.pdfFile = null, this.file = null
                }, mounted: function () {
                    this.screenWidth = document.body.clientWidth;
                    var e = this;
                    this.$refs.contentDiv.addEventListener("scroll", this.scrool), window.onresize = function () {
                        return function () {
                            e.screenWidth = document.body.clientWidth - 88;
                            var t = Object(a["d"])(e.screenWidth, e.ofdObj);
                            e.displayOfdDiv(t)
                        }()
                    };
                    var t = window.location.search.substring(1);
                    t && this.demo(t)
                }, methods: {
                    scrool: function () {
                        for (var e, t, n = (null === (e = this.$refs.contentDiv.firstElementChild) || void 0 === e || null === (t = e.getBoundingClientRect()) || void 0 === t ? void 0 : t.top) - 60, s = 0, a = 0, r = 0; r < this.$refs.contentDiv.childElementCount; r++) {
                            var i, o;
                            if (s += Math.abs(null === (i = this.$refs.contentDiv.children.item(r)) || void 0 === i ? void 0 : i.style.height.replace("px", "")) + Math.abs(null === (o = this.$refs.contentDiv.children.item(r)) || void 0 === o ? void 0 : o.style.marginBottom.replace("px", "")), Math.abs(n) < s) {
                                a = r;
                                break
                            }
                        }
                        this.pageIndex = a + 1
                    }, closeSealInfoDialog: function () {
                        this.$refs.sealInfoDiv.setAttribute("style", "display: none"), document.getElementById("spSigner").innerText = "[无效的签章结构]", document.getElementById("spProvider").innerText = "[无效的签章结构]", document.getElementById("spHashedValue").innerText = "[无效的签章结构]", document.getElementById("spSignedValue").innerText = "[无效的签章结构]", document.getElementById("spSignMethod").innerText = "[无效的签章结构]", document.getElementById("spSealID").innerText = "[无效的签章结构]", document.getElementById("spSealName").innerText = "[无效的签章结构]", document.getElementById("spSealType").innerText = "[无效的签章结构]", document.getElementById("spSealAuthTime").innerText = "[无效的签章结构]", document.getElementById("spSealMakeTime").innerText = "[无效的签章结构]", document.getElementById("spSealVersion").innerText = "[无效的签章结构]", document.getElementById("spVersion").innerText = "[无效的签章结构]", document.getElementById("VerifyRet").innerText = "[无效的签章结构]"
                    }, showMore: function (e, t) {
                        this.dialogFormVisible = !0, this.value = document.getElementById(t).innerText, this.title = e
                    }, downOfd: function (e) {
                        var t = this, n = this;
                        this.loading = !0, this.$axios({
                            method: "post",
                            url: "https://51shouzu.xyz/api/ofd/convertOfd",
                            data: {pdfBase64: e}
                        }).then((function (e) {
                            n.loading = !1;
                            for (var t = atob(e.data.data.replace(/\s/g, "")), s = t.length, a = new ArrayBuffer(s), r = new Uint8Array(a), i = 0; i < s; i++) r[i] = t.charCodeAt(i);
                            var o = new Blob([r], null), u = URL.createObjectURL(o), c = document.createElement("a");
                            c.style.display = "none", c.href = u, c.setAttribute("download", "ofd.ofd"), document.body.appendChild(c), c.click()
                        })).catch((function (e) {
                            console.log(e, "error"), n.$alert("PDF打开失败", e, {
                                confirmButtonText: "确定",
                                callback: function (e) {
                                    t.$message({type: "info", message: "action: ".concat(e)})
                                }
                            })
                        }))
                    }, downPdf: function () {
                        var e = this, t = this;
                        this.loading = !0, this.$axios({
                            method: "post",
                            url: "https://51shouzu.xyz/api/ofd/convertPdf",
                            data: {ofdBase64: this.ofdBase64}
                        }).then((function (e) {
                            t.loading = !1;
                            for (var n = atob(e.data.data.replace(/\s/g, "")), s = n.length, a = new ArrayBuffer(s), r = new Uint8Array(a), i = 0; i < s; i++) r[i] = n.charCodeAt(i);
                            var o = new Blob([r], {type: "application/pdf"}), u = URL.createObjectURL(o),
                                c = document.createElement("a");
                            c.style.display = "none", c.href = u, c.setAttribute("download", "ofd.pdf"), document.body.appendChild(c), c.click()
                        })).catch((function (n) {
                            console.log(n, "error"), t.$alert("OFD打开失败", n, {
                                confirmButtonText: "确定",
                                callback: function (t) {
                                    e.$message({type: "info", message: "action: ".concat(t)})
                                }
                            })
                        }))
                    }, downloadFile: function () {
                        var e = this, t = document.createElement("a");
                        t.style.display = "none", t.href = e.filePath, document.body.appendChild(t), t.click()
                    }, doPrint: function () {
                        var e = this;
                        document.body.innerHTML = e.newStr, window.print(), window.location.reload()
                    }, plus: function () {
                        Object(a["f"])(++this.scale);
                        var e = Object(a["e"])(this.ofdObj);
                        this.displayOfdDiv(e)
                    }, minus: function () {
                        Object(a["f"])(--this.scale);
                        var e = Object(a["e"])(this.ofdObj);
                        this.displayOfdDiv(e)
                    }, prePage: function () {
                        var e = document.getElementById("content"), t = e.children.item(this.pageIndex - 2);
                        null === t || void 0 === t || t.scrollIntoView(!0), t && (this.pageIndex = this.pageIndex - 1)
                    }, firstPage: function () {
                        var e = document.getElementById("content"), t = e.firstElementChild;
                        null === t || void 0 === t || t.scrollIntoView(!0), t && (this.pageIndex = 1)
                    }, nextPage: function () {
                        var e = document.getElementById("content"), t = e.children.item(this.pageIndex);
                        null === t || void 0 === t || t.scrollIntoView(!0), t && ++this.pageIndex
                    }, lastPage: function () {
                        var e = document.getElementById("content"), t = e.lastElementChild;
                        null === t || void 0 === t || t.scrollIntoView(!0), t && (this.pageIndex = e.childElementCount)
                    }, demo: function (e) {
                        var t = e, n = this;
                        n.filePath = t, r["getBinaryContent"](t, (function (e, t) {
                            if (e) console.log(e); else {
                                var s = btoa(String.fromCharCode.apply(null, new Uint8Array(t)));
                                n.ofdBase64 = s
                            }
                        })), this.getOfdDocumentObj(t, this.screenWidth)
                    }, uploadFile: function () {
                        this.file = null, this.$refs.file.click()
                    }, fileChanged: function () {
                        var e = this;
                        this.file = this.$refs.file.files[0], this.filePath = this.file;
                        var t = this.file.name.replace(/.+\./, "");
                        if (-1 !== ["ofd"].indexOf(t)) if (this.file.size > 104857600) this.$alert("error", "文件大小需 < 100M", {
                            confirmButtonText: "确定",
                            callback: function (t) {
                                e.$message({type: "info", message: "action: ".concat(t)})
                            }
                        }); else {
                            var n = this, s = new FileReader;
                            s.readAsDataURL(this.file), s.onload = function (e) {
                                n.ofdBase64 = e.target.result.split(",")[1]
                            }, this.getOfdDocumentObj(this.file, this.screenWidth), this.$refs.file.value = null
                        } else this.$alert("error", "仅支持ofd类型", {
                            confirmButtonText: "确定", callback: function (t) {
                                e.$message({type: "info", message: "action: ".concat(t)})
                            }
                        })
                    }, uploadPdfFile: function () {
                        this.pdfFile = null, this.$refs.pdfFile.click()
                    }, pdfFileChanged: function () {
                        var e = this;
                        this.pdfFile = this.$refs.pdfFile.files[0];
                        var t = this.pdfFile.name.replace(/.+\./, "");
                        if (-1 !== ["pdf"].indexOf(t)) if (this.pdfFile.size > 104857600) this.$alert("error", "文件大小需 < 100M", {
                            confirmButtonText: "确定",
                            callback: function (t) {
                                e.$message({type: "info", message: "action: ".concat(t)})
                            }
                        }); else {
                            var n = this, s = new FileReader;
                            s.readAsDataURL(this.pdfFile), s.onload = function (e) {
                                var t = e.target.result.split(",")[1];
                                n.downOfd(t)
                            }, this.$refs.pdfFile.value = null
                        } else this.$alert("error", "仅支持pdf类型", {
                            confirmButtonText: "确定", callback: function (t) {
                                e.$message({type: "info", message: "action: ".concat(t)})
                            }
                        })
                    }, getOfdDocumentObj: function (e, t) {
                        var n = this, s = (new Date).getTime();
                        this.loading = !0, Object(a["c"])({
                            ofd: e, success: function (e) {
                                console.log(e);
                                var r = (new Date).getTime();
                                console.log("解析ofd", r - s), n.ofdObj = e[0], n.pageCount = e[0].pages.length;
                                var i = Object(a["d"])(t, e[0]), o = (new Date).getTime();
                                console.log("xml转svg", o - r), n.displayOfdDiv(i);
                                var u = (new Date).getTime();
                                document.getElementById('1').getElementsByTagName('svg')[0].style.display='none';
                                console.log("svg渲染到页面", u - o), n.loading = !1, n.newStr = document.getElementById("printDiv").innerHTML
                            }, fail: function (e) {
                                var t = this;
                                n.loading = !1, n.$alert("OFD打开失败", e, {
                                    confirmButtonText: "确定",
                                    callback: function (e) {
                                        t.$message({type: "info", message: "action: ".concat(e)})
                                    }
                                })
                            }
                        })
                    }, displayOfdDiv: function (e) {
                        this.scale = Object(a["b"])();
                        var t = document.getElementById("content");
                        t.innerHTML = "";
                        var n = document.createElement("div");
                        n.id = "printDiv", n.setAttribute("style", "margin-bottom: 20px"), t.appendChild(n);
                        var r, i = Object(s["a"])(e);
                        try {
                            for (i.s(); !(r = i.n()).done;) {
                                var o = r.value;
                                n.appendChild(o)
                            }
                        } catch (b) {
                            i.e(b)
                        } finally {
                            i.f()
                        }
                        document.getElementById('1').getElementsByTagName('svg')[0].style.display='none';
                        var u, c = Object(s["a"])(document.getElementsByName("seal_img_div"));
                        try {
                            for (c.s(); !(u = c.n()).done;) {
                                var l = u.value;
                                this.addEventOnSealDiv(l, JSON.parse(l.dataset.sesSignature), JSON.parse(l.dataset.signedInfo))
                            }
                        } catch (b) {
                            c.e(b)
                        } finally {
                            c.f()
                        }
                    }, addEventOnSealDiv: function (t, n, s) {
                        try {
                            e.HashRet = null, e.VerifyRet = s.VerifyRet, t.addEventListener("click", (function () {
                                document.getElementById("sealInfoDiv").hidden = !1, document.getElementById("sealInfoDiv").setAttribute("style", "display:flex;align-items: center;justify-content: center;"), n.realVersion < 4 ? (document.getElementById("spSigner").innerText = n.toSign.cert["commonName"], document.getElementById("spProvider").innerText = s.Provider["@_ProviderName"], document.getElementById("spHashedValue").innerText = n.toSign.dataHash.replace(/\n/g, ""), document.getElementById("spSignedValue").innerText = n.signature.replace(/\n/g, ""), document.getElementById("spSignMethod").innerText = n.toSign.signatureAlgorithm.replace(/\n/g, ""), document.getElementById("spSealID").innerText = n.toSign.eseal.esealInfo.esID, document.getElementById("spSealName").innerText = n.toSign.eseal.esealInfo.property.name, document.getElementById("spSealType").innerText = n.toSign.eseal.esealInfo.property.type, document.getElementById("spSealAuthTime").innerText = "从 " + n.toSign.eseal.esealInfo.property.validStart + " 到 " + n.toSign.eseal.esealInfo.property.validEnd, document.getElementById("spSealMakeTime").innerText = n.toSign.eseal.esealInfo.property.createDate, document.getElementById("spSealVersion").innerText = n.toSign.eseal.esealInfo.header.version) : (document.getElementById("spSigner").innerText = n.cert["commonName"], document.getElementById("spProvider").innerText = s.Provider["@_ProviderName"], document.getElementById("spHashedValue").innerText = n.toSign.dataHash.replace(/\n/g, ""), document.getElementById("spSignedValue").innerText = n.signature.replace(/\n/g, ""), document.getElementById("spSignMethod").innerText = n.signatureAlgID.replace(/\n/g, ""), document.getElementById("spSealID").innerText = n.toSign.eseal.esealInfo.esID, document.getElementById("spSealName").innerText = n.toSign.eseal.esealInfo.property.name, document.getElementById("spSealType").innerText = n.toSign.eseal.esealInfo.property.type, document.getElementById("spSealAuthTime").innerText = "从 " + n.toSign.eseal.esealInfo.property.validStart + " 到 " + n.toSign.eseal.esealInfo.property.validEnd, document.getElementById("spSealMakeTime").innerText = n.toSign.eseal.esealInfo.property.createDate, document.getElementById("spSealVersion").innerText = n.toSign.eseal.esealInfo.header.version), document.getElementById("spVersion").innerText = n.toSign.version, document.getElementById("VerifyRet").innerText = "文件摘要值后台验证中，请稍等... " + (e.VerifyRet ? "签名值验证成功" : "签名值验证失败"), (null == e.HashRet || void 0 == e.HashRet || Object.keys(e.HashRet).length <= 0) && setTimeout((function () {
                                    var t = e.VerifyRet ? "签名值验证成功" : "签名值验证失败";
                                    e.HashRet = Object(a["a"])(e.toBeChecked.get(s.signatureID));
                                    var n = e.HashRet ? "文件摘要值验证成功" : "文件摘要值验证失败";
                                    document.getElementById("VerifyRet").innerText = n + " " + t
                                }), 1e3)
                            }))
                        } catch (r) {
                            console.log(r)
                        }
                        e.VerifyRet || t.setAttribute("class", "gray")
                    }
                }
            }
        }).call(this, n("c8ba"))
    }, "85ec": function (e, t, n) {
    }, a9c6: function (e, t, n) {
        "use strict";
        n.d(t, "b", (function () {
            return N
        })), n.d(t, "a", (function () {
            return H
        }));
        n("4160"), n("c975"), n("4ec9"), n("d3b7"), n("ac1f"), n("3ca3"), n("5319"), n("1276"), n("4c53"), n("159b"), n("ddb0");
        var s = n("b85c"), a = (n("96cf"), n("1da1")), r = n("6f9c"), i = n.n(r), o = n("64c1"), u = n.n(o),
            c = n("a476"), l = n.n(c), b = n("8060");
        n("99af"), n("a15b"), n("13d5"), n("25f0"), n("f5b2");

        function d(e, t) {
            return e.length >= t ? e : new Array(t - e.length + 1).join("0") + e
        }

        function f(e) {
            for (var t = 8, n = "", s = 0; s < e.length / t; s++) n += d(parseInt(e.substr(s * t, t), 2).toString(16), 2);
            return n
        }

        function h(e) {
            for (var t = 2, n = "", s = 0; s < e.length / t; s++) n += d(parseInt(e.substr(s * t, t), 16).toString(2), 8);
            return n
        }

        function p(e, t) {
            return e.substring(t % e.length) + e.substr(0, t % e.length)
        }

        function v(e, t, n) {
            for (var s, a = e || "", r = t || "", i = [], o = a.length - 1; o >= 0; o--) s = n(a[o], r[o], s), i[o] = s[0];
            return i.join("")
        }

        function m(e, t) {
            return v(e, t, (function (e, t) {
                return [e === t ? "0" : "1"]
            }))
        }

        function g(e, t) {
            return v(e, t, (function (e, t) {
                return ["1" === e && "1" === t ? "1" : "0"]
            }))
        }

        function y(e, t) {
            return v(e, t, (function (e, t) {
                return ["1" === e || "1" === t ? "1" : "0"]
            }))
        }

        function x(e, t) {
            var n = v(e, t, (function (e, t, n) {
                var s = n ? n[1] : "0";
                return e !== t ? ["0" === s ? "1" : "0", s] : [s, e]
            }));
            return n
        }

        function w(e) {
            return v(e, void 0, (function (e) {
                return ["1" === e ? "0" : "1"]
            }))
        }

        function O(e) {
            return function () {
                for (var t = arguments.length, n = new Array(t), s = 0; s < t; s++) n[s] = arguments[s];
                return n.reduce((function (t, n) {
                    return e(t, n)
                }))
            }
        }

        function S(e) {
            return O(m)(e, p(e, 9), p(e, 17))
        }

        function j(e) {
            return O(m)(e, p(e, 15), p(e, 23))
        }

        function k(e, t, n, s) {
            return s >= 0 && s <= 15 ? O(m)(e, t, n) : O(y)(g(e, t), g(e, n), g(t, n))
        }

        function I(e, t, n, s) {
            return s >= 0 && s <= 15 ? O(m)(e, t, n) : y(g(e, t), g(w(e), n))
        }

        function C(e) {
            return h(e >= 0 && e <= 15 ? "79cc4519" : "7a879d8a")
        }

        function _(e, t) {
            for (var n = 32, s = [], a = [], r = 0; r < 16; r++) s.push(t.substr(r * n, n));
            for (var i = 16; i < 68; i++) s.push(O(m)(j(O(m)(s[i - 16], s[i - 9], p(s[i - 3], 15))), p(s[i - 13], 7), s[i - 6]));
            for (var o = 0; o < 64; o++) a.push(m(s[o], s[o + 4]));
            for (var u = [], c = 0; c < 8; c++) u.push(e.substr(c * n, n));
            for (var l, b, d, f, h = u[0], v = u[1], g = u[2], y = u[3], w = u[4], _ = u[5], A = u[6], B = u[7], D = 0; D < 64; D++) l = p(O(x)(p(h, 12), w, p(C(D), D)), 7), b = m(l, p(h, 12)), d = O(x)(k(h, v, g, D), y, b, a[D]), f = O(x)(I(w, _, A, D), B, l, s[D]), y = g, g = p(v, 9), v = h, h = d, B = A, A = p(_, 19), _ = w, w = S(f);
            return m([h, v, g, y, w, _, A, B].join(""), e)
        }

        function A(e) {
            var t = h(e), n = t.length, s = n % 512;
            s = s >= 448 ? 512 - s % 448 - 1 : 448 - s - 1;
            for (var a = "".concat(t, "1").concat(d("", s)).concat(d(n.toString(2), 64)).toString(), r = (n + s + 65) / 512, i = h("7380166f4914b2b9172442d7da8a0600a96f30bc163138aae38dee4db0fb0e4e"), o = 0; o <= r - 1; o++) {
                var u = a.substr(512 * o, 512);
                i = _(i, u)
            }
            return f(i)
        }

        var B = n("8237"), D = n.n(B), T = n("6199"), P = n.n(T), F = n("81fa"), R = n.n(F), E = n("6b33"),
            L = function (e, t, n) {
                var s = Object(E["a"])(u.a.decode(t));
                return n = n.toLowerCase(), n.indexOf("1.2.156.10197.1.401") >= 0 || n.indexOf("sm3") >= 0 ? s == A(Object(E["a"])(e)) : n.indexOf("md5") >= 0 ? s == D()(e) : n.indexOf("sha1") >= 0 ? s == P()(e) : ""
            }, V = function (e) {
                try {
                    var t = e.realVersion < 4 ? e.toSign.signatureAlgorithm : e.signatureAlgID;
                    t = t.toLowerCase();
                    var n = e.toSignDer;
                    if (t.indexOf("1.2.156.10197.1.501") >= 0 || t.indexOf("sm2") >= 0) {
                        var s = e.signature.replace(/ /g, "").replace(/\n/g, "");
                        0 == s.indexOf("00") && (s = s.substr(2, s.length - 2));
                        var a = e.realVersion < 4 ? e.toSign.cert : e.cert,
                            r = a.subjectPublicKeyInfo.subjectPublicKey.replace(/ /g, "").replace(/\n/g, "");
                        return 0 == r.indexOf("00") && (r = r.substr(2, r.length - 2)), b["sm2"].doVerifySignature(n, s, r, {
                            der: !0,
                            hash: !0,
                            userId: "1234567812345678"
                        })
                    }
                    new R.a.KJUR.crypto.Signature({alg: "SHA1withRSA"}), e.realVersion < 4 ? e.toSign.cert : e.cert;
                    var i = e.signature.replace(/ /g, "").replace(/\n/g, "");
                    return 0 == i.indexOf("00") && (i = i.substr(2, i.length - 2)), !0
                } catch (o) {
                    return console.log(o), !1
                }
            }, M = /^\s*(?:[0-9A-Fa-f][0-9A-Fa-f]\s*)+$/, N = function () {
                var e = Object(a["a"])(regeneratorRuntime.mark((function e(t, n) {
                    return regeneratorRuntime.wrap((function (e) {
                        while (1) switch (e.prev = e.next) {
                            case 0:
                                return e.abrupt("return", new Promise((function (e, s) {
                                    t.files[n].async("base64").then((function (t) {
                                        var n = q(t);
                                        e(n)
                                    }), (function (e) {
                                        s(e)
                                    }))
                                })));
                            case 1:
                            case"end":
                                return e.stop()
                        }
                    }), e)
                })));
                return function (t, n) {
                    return e.apply(this, arguments)
                }
            }(), H = function (e) {
                var t, n = !0, a = Object(s["a"])(e);
                try {
                    for (a.s(); !(t = a.n()).done;) {
                        var r = t.value, i = L(r.fileData, r.hashed, r.checkMethod);
                        n = n && i
                    }
                } catch (o) {
                    a.e(o)
                } finally {
                    a.f()
                }
                return n
            }, q = function (e) {
                try {
                    var t;
                    return M.test(e) ? (console.log("Hex"), t = i.a.decode(e)) : (console.log("Base64"), t = u.a.unarmor(e)), U(t)
                } catch (n) {
                    return console.log(n), {}
                }
            }, U = function (e, t) {
                t = t || 0;
                try {
                    var n = $(e, t), s = n.toSign.eseal.esealInfo.picture.type,
                        a = n.toSign.eseal.esealInfo.picture.data.byte;
                    return {ofdArray: a, type: s.toLowerCase(), SES_Signature: n, verifyRet: V(n)}
                } catch (r) {
                    return console.log(r), {}
                }
            }, W = function (e) {
                e = e.replace("Unrecognized time: ", "");
                e.indexOf("Z");
                return e = e.replace("Z", ""), e = e.substr(0, 1) < "5" ? "20" + e : "19" + e, e
            }, $ = function (e, t) {
                t = t || 0;
                var n, s = l.a.decode(e, t);
                try {
                    var a, r, i, o, u, c, b, d, f, h, p, v, m, g, y, x, w, O, S, j, k, I, C, _, A, B, D, T, P, F, R, E, L,
                        V, M, N, H, q, U, $, J, K, X, Y, Q, Z, ee, te, ne, se, ae, re, ie, oe, ue, ce, le, be, de, fe, he,
                        pe, ve, me, ge, ye, xe, we, Oe, Se, je, ke, Ie, Ce, _e, Ae, Be, De, Te, Pe, Fe, Re, Ee, Le, Ve, Me,
                        Ne, He, qe, Ue, We, $e, ze, Ge, Je, Ke, Xe, Ye, Qe, Ze, et, tt,
                        nt = W(null === (a = s.sub[0]) || void 0 === a || null === (r = a.sub[1]) || void 0 === r || null === (i = r.sub[0]) || void 0 === i || null === (o = i.sub[2]) || void 0 === o || null === (u = o.sub[3]) || void 0 === u ? void 0 : u.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[3].header, s.sub[0].sub[1].sub[0].sub[2].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[3].header + s.sub[0].sub[1].sub[0].sub[2].sub[3].length)),
                        st = W(null === (c = s.sub[0]) || void 0 === c || null === (b = c.sub[1]) || void 0 === b || null === (d = b.sub[0]) || void 0 === d || null === (f = d.sub[2]) || void 0 === f || null === (h = f.sub[4]) || void 0 === h ? void 0 : h.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[4].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[4].header, s.sub[0].sub[1].sub[0].sub[2].sub[4].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[4].header + s.sub[0].sub[1].sub[0].sub[2].sub[4].length)),
                        at = W(null === (p = s.sub[0]) || void 0 === p || null === (v = p.sub[1]) || void 0 === v || null === (m = v.sub[0]) || void 0 === m || null === (g = m.sub[2]) || void 0 === g || null === (y = g.sub[5]) || void 0 === y ? void 0 : y.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[5].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[5].header, s.sub[0].sub[1].sub[0].sub[2].sub[5].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[5].header + s.sub[0].sub[1].sub[0].sub[2].sub[5].length)),
                        rt = W(null === (x = s.sub[0]) || void 0 === x || null === (w = x.sub[2]) || void 0 === w ? void 0 : w.stream.parseTime(s.sub[0].sub[2].stream.pos + s.sub[0].sub[2].header, s.sub[0].sub[2].stream.pos + s.sub[0].sub[2].header + s.sub[0].sub[2].length, !1)),
                        it = null === (O = s.sub[0]) || void 0 === O || null === (S = O.sub[1]) || void 0 === S || null === (j = S.sub[0]) || void 0 === j || null === (k = j.sub[2]) || void 0 === k ? void 0 : k.sub[2],
                        ot = new Array;
                    it && it.sub.forEach((function (e) {
                        ot.push(e.stream.parseOctetString(e.stream.pos + e.header, e.stream.pos + e.header + e.length))
                    }));
                    var ut = null === (I = s.sub[0]) || void 0 === I || null === (C = I.sub[1]) || void 0 === C || null === (_ = C.sub[0]) || void 0 === _ ? void 0 : _.sub[4],
                        ct = new Array;
                    ut && ut.sub.forEach((function (e) {
                        var t, n, s;
                        ct.push({
                            extnID: null === (t = e.sub[0]) || void 0 === t ? void 0 : t.stream.parseOID(e.sub[0].stream.pos + e.sub[0].header, e.sub[0].stream.pos + e.sub[0].header + e.sub[0].length),
                            critical: null === (n = e.sub[1]) || void 0 === n ? void 0 : n.stream.parseInteger(e.sub[1].stream.pos + e.sub[1].header, e.sub[1].stream.pos + e.sub[1].header + e.sub[1].length),
                            extnValue: null === (s = e.sub[2]) || void 0 === s ? void 0 : s.stream.parseOctetString(e.sub[2].stream.pos + e.sub[2].header, e.sub[2].stream.pos + e.sub[2].header + e.sub[2].length)
                        })
                    })), n = {
                        realVersion: 1,
                        toSignDer: null === (A = s.sub[0]) || void 0 === A ? void 0 : A.stream.enc.subarray(s.sub[0].stream.pos, s.sub[0].stream.pos + s.sub[0].header + s.sub[0].length),
                        toSign: {
                            version: null === (B = s.sub[0]) || void 0 === B || null === (D = B.sub[0]) || void 0 === D ? void 0 : D.stream.parseInteger(s.sub[0].sub[0].stream.pos + s.sub[0].sub[0].header, s.sub[0].sub[0].stream.pos + s.sub[0].sub[0].header + s.sub[0].sub[0].length),
                            eseal: {
                                esealInfo: {
                                    header: {
                                        ID: null === (T = s.sub[0]) || void 0 === T || null === (P = T.sub[1]) || void 0 === P || null === (F = P.sub[0]) || void 0 === F || null === (R = F.sub[0]) || void 0 === R || null === (E = R.sub[0]) || void 0 === E ? void 0 : E.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[0].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[0].header, s.sub[0].sub[1].sub[0].sub[0].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[0].header + s.sub[0].sub[1].sub[0].sub[0].sub[0].length),
                                        version: null === (L = s.sub[0]) || void 0 === L || null === (V = L.sub[1]) || void 0 === V || null === (M = V.sub[0]) || void 0 === M || null === (N = M.sub[0]) || void 0 === N || null === (H = N.sub[1]) || void 0 === H ? void 0 : H.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[1].header, s.sub[0].sub[1].sub[0].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[1].header + s.sub[0].sub[1].sub[0].sub[0].sub[1].length),
                                        Vid: null === (q = s.sub[0]) || void 0 === q || null === (U = q.sub[1]) || void 0 === U || null === ($ = U.sub[0]) || void 0 === $ || null === (J = $.sub[0]) || void 0 === J || null === (K = J.sub[2]) || void 0 === K ? void 0 : K.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[0].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[2].header, s.sub[0].sub[1].sub[0].sub[0].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[2].header + s.sub[0].sub[1].sub[0].sub[0].sub[2].length)
                                    },
                                    esID: null === (X = s.sub[0]) || void 0 === X || null === (Y = X.sub[1]) || void 0 === Y || null === (Q = Y.sub[0]) || void 0 === Q || null === (Z = Q.sub[1]) || void 0 === Z ? void 0 : Z.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[1].header, s.sub[0].sub[1].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[1].header + s.sub[0].sub[1].sub[0].sub[1].length),
                                    property: {
                                        type: null === (ee = s.sub[0]) || void 0 === ee || null === (te = ee.sub[1]) || void 0 === te || null === (ne = te.sub[0]) || void 0 === ne || null === (se = ne.sub[2]) || void 0 === se || null === (ae = se.sub[0]) || void 0 === ae ? void 0 : ae.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[2].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[0].header, s.sub[0].sub[1].sub[0].sub[2].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[0].header + s.sub[0].sub[1].sub[0].sub[2].sub[0].length),
                                        name: null === (re = s.sub[0]) || void 0 === re || null === (ie = re.sub[1]) || void 0 === ie || null === (oe = ie.sub[0]) || void 0 === oe || null === (ue = oe.sub[2]) || void 0 === ue || null === (ce = ue.sub[1]) || void 0 === ce ? void 0 : ce.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[2].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[1].header, s.sub[0].sub[1].sub[0].sub[2].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[1].header + s.sub[0].sub[1].sub[0].sub[2].sub[1].length),
                                        certList: ot,
                                        createDate: nt,
                                        validStart: st,
                                        validEnd: at
                                    },
                                    picture: {
                                        type: null === (le = s.sub[0]) || void 0 === le || null === (be = le.sub[1]) || void 0 === be || null === (de = be.sub[0]) || void 0 === de || null === (fe = de.sub[3]) || void 0 === fe || null === (he = fe.sub[0]) || void 0 === he ? void 0 : he.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[3].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[0].header, s.sub[0].sub[1].sub[0].sub[3].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[0].header + s.sub[0].sub[1].sub[0].sub[3].sub[0].length),
                                        data: {
                                            hex: null === (pe = s.sub[0]) || void 0 === pe || null === (ve = pe.sub[1]) || void 0 === ve || null === (me = ve.sub[0]) || void 0 === me || null === (ge = me.sub[3]) || void 0 === ge || null === (ye = ge.sub[1]) || void 0 === ye ? void 0 : ye.stream.parseOctetString(s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header, s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header + s.sub[0].sub[1].sub[0].sub[3].sub[1].length),
                                            byte: null === (xe = s.sub[0]) || void 0 === xe || null === (we = xe.sub[1]) || void 0 === we || null === (Oe = we.sub[0]) || void 0 === Oe || null === (Se = Oe.sub[3]) || void 0 === Se || null === (je = Se.sub[1]) || void 0 === je ? void 0 : je.stream.enc.subarray(s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header, s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header + s.sub[0].sub[1].sub[0].sub[3].sub[1].length)
                                        },
                                        width: null === (ke = s.sub[0]) || void 0 === ke || null === (Ie = ke.sub[1]) || void 0 === Ie || null === (Ce = Ie.sub[0]) || void 0 === Ce || null === (_e = Ce.sub[3]) || void 0 === _e || null === (Ae = _e.sub[2]) || void 0 === Ae ? void 0 : Ae.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[3].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[2].header, s.sub[0].sub[1].sub[0].sub[3].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[2].header + s.sub[0].sub[1].sub[0].sub[3].sub[2].length),
                                        height: null === (Be = s.sub[0]) || void 0 === Be || null === (De = Be.sub[1]) || void 0 === De || null === (Te = De.sub[0]) || void 0 === Te || null === (Pe = Te.sub[3]) || void 0 === Pe || null === (Fe = Pe.sub[3]) || void 0 === Fe ? void 0 : Fe.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[3].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[3].header, s.sub[0].sub[1].sub[0].sub[3].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[3].header + s.sub[0].sub[1].sub[0].sub[3].sub[3].length)
                                    },
                                    extDatas: ct
                                },
                                signInfo: {
                                    cert: z(null === (Re = s.sub[0]) || void 0 === Re || null === (Ee = Re.sub[1]) || void 0 === Ee || null === (Le = Ee.sub[1]) || void 0 === Le ? void 0 : Le.sub[0]),
                                    signatureAlgorithm: null === (Ve = s.sub[0]) || void 0 === Ve || null === (Me = Ve.sub[1]) || void 0 === Me || null === (Ne = Me.sub[1]) || void 0 === Ne || null === (He = Ne.sub[1]) || void 0 === He ? void 0 : He.stream.parseOID(s.sub[0].sub[1].sub[1].sub[1].stream.pos + s.sub[0].sub[1].sub[1].sub[1].header, s.sub[0].sub[1].sub[1].sub[1].stream.pos + s.sub[0].sub[1].sub[1].sub[1].header + s.sub[0].sub[1].sub[1].sub[1].length),
                                    signData: null === (qe = s.sub[0]) || void 0 === qe || null === (Ue = qe.sub[1]) || void 0 === Ue || null === (We = Ue.sub[1]) || void 0 === We || null === ($e = We.sub[2]) || void 0 === $e ? void 0 : $e.stream.hexDump(s.sub[0].sub[1].sub[1].sub[2].stream.pos + s.sub[0].sub[1].sub[1].sub[2].header, s.sub[0].sub[1].sub[1].sub[2].stream.pos + s.sub[0].sub[1].sub[1].sub[2].header + s.sub[0].sub[1].sub[1].sub[2].length, !1)
                                }
                            },
                            timeInfo: rt,
                            dataHash: null === (ze = s.sub[0]) || void 0 === ze || null === (Ge = ze.sub[3]) || void 0 === Ge ? void 0 : Ge.stream.hexDump(s.sub[0].sub[3].stream.pos + s.sub[0].sub[3].header, s.sub[0].sub[3].stream.pos + s.sub[0].sub[3].header + s.sub[0].sub[3].length, !1),
                            propertyInfo: null === (Je = s.sub[0]) || void 0 === Je || null === (Ke = Je.sub[4]) || void 0 === Ke ? void 0 : Ke.stream.parseStringUTF(s.sub[0].sub[4].stream.pos + s.sub[0].sub[4].header, s.sub[0].sub[4].stream.pos + s.sub[0].sub[4].header + s.sub[0].sub[4].length),
                            cert: z(null === (Xe = s.sub[0]) || void 0 === Xe || null === (Ye = Xe.sub[1]) || void 0 === Ye || null === (Qe = Ye.sub[1]) || void 0 === Qe ? void 0 : Qe.sub[0]),
                            signatureAlgorithm: null === (Ze = s.sub[0]) || void 0 === Ze || null === (et = Ze.sub[6]) || void 0 === et ? void 0 : et.stream.parseOID(s.sub[0].sub[6].stream.pos + s.sub[0].sub[6].header, s.sub[0].sub[6].stream.pos + s.sub[0].sub[6].header + s.sub[0].sub[6].length)
                        },
                        signature: null === (tt = s.sub[1]) || void 0 === tt ? void 0 : tt.stream.hexDump(s.sub[1].stream.pos + s.sub[1].header, s.sub[1].stream.pos + s.sub[1].header + s.sub[1].length, !1)
                    }
                } catch (os) {
                    try {
                        var lt, bt, dt, ft, ht, pt, vt, mt, gt, yt, xt, wt, Ot, St, jt, kt, It, Ct, _t, At, Bt, Dt, Tt, Pt,
                            Ft, Rt, Et, Lt, Vt, Mt, Nt, Ht, qt, Ut, Wt, $t, zt, Gt, Jt, Kt, Xt, Yt, Qt, Zt, en, tn, nn, sn,
                            an, rn, on, un, cn, ln, bn, dn, fn, hn, pn, vn, mn, gn, yn, xn, wn, On, Sn, jn, kn, In, Cn, _n,
                            An, Bn, Dn, Tn, Pn, Fn, Rn, En, Ln, Vn, Mn, Nn, Hn, qn, Un, Wn, $n, zn, Gn, Jn, Kn, Xn, Yn, Qn,
                            Zn, es, ts,
                            ns = null === (lt = s.sub[0]) || void 0 === lt || null === (bt = lt.sub[1]) || void 0 === bt || null === (dt = bt.sub[0]) || void 0 === dt || null === (ft = dt.sub[2]) || void 0 === ft || null === (ht = ft.sub[2]) || void 0 === ht ? void 0 : ht.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[2].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[2].header, s.sub[0].sub[1].sub[0].sub[2].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[2].header + s.sub[0].sub[1].sub[0].sub[2].sub[2].length),
                            ss = null === (pt = s.sub[0]) || void 0 === pt || null === (vt = pt.sub[1]) || void 0 === vt || null === (mt = vt.sub[0]) || void 0 === mt || null === (gt = mt.sub[2]) || void 0 === gt ? void 0 : gt.sub[3],
                            as = new Array;
                        ss && ss.sub.forEach((function (e) {
                            as.push(e.stream.parseOctetString(e.stream.pos + e.header, e.stream.pos + e.header + e.length))
                        }));
                        var rs = null === (yt = s.sub[0]) || void 0 === yt || null === (xt = yt.sub[1]) || void 0 === xt || null === (wt = xt.sub[0]) || void 0 === wt ? void 0 : wt.sub[4],
                            is = new Array;
                        rs && rs.sub.forEach((function (e) {
                            var t, n, s;
                            is.push({
                                extnID: null === (t = e.sub[0]) || void 0 === t ? void 0 : t.stream.parseOID(e.sub[0].stream.pos + e.sub[0].header, e.sub[0].stream.pos + e.sub[0].header + e.sub[0].length),
                                critical: null === (n = e.sub[1]) || void 0 === n ? void 0 : n.stream.parseInteger(e.sub[1].stream.pos + e.sub[1].header, e.sub[1].stream.pos + e.sub[1].header + e.sub[1].length),
                                extnValue: null === (s = e.sub[2]) || void 0 === s ? void 0 : s.stream.parseOctetString(e.sub[2].stream.pos + e.sub[2].header, e.sub[2].stream.pos + e.sub[2].header + e.sub[2].length)
                            })
                        })), n = {
                            realVersion: 4,
                            toSignDer: null === (Ot = s.sub[0]) || void 0 === Ot ? void 0 : Ot.stream.enc.subarray(s.sub[0].stream.pos, s.sub[0].stream.pos + s.sub[0].header + s.sub[0].length),
                            toSign: {
                                version: null === (St = s.sub[0]) || void 0 === St || null === (jt = St.sub[0]) || void 0 === jt ? void 0 : jt.stream.parseInteger(s.sub[0].sub[0].stream.pos + s.sub[0].sub[0].header, s.sub[0].sub[0].stream.pos + s.sub[0].sub[0].header + s.sub[0].sub[0].length),
                                eseal: {
                                    esealInfo: {
                                        header: {
                                            ID: null === (kt = s.sub[0]) || void 0 === kt || null === (It = kt.sub[1]) || void 0 === It || null === (Ct = It.sub[0]) || void 0 === Ct || null === (_t = Ct.sub[0]) || void 0 === _t || null === (At = _t.sub[0]) || void 0 === At ? void 0 : At.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[0].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[0].header, s.sub[0].sub[1].sub[0].sub[0].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[0].header + s.sub[0].sub[1].sub[0].sub[0].sub[0].length),
                                            version: null === (Bt = s.sub[0]) || void 0 === Bt || null === (Dt = Bt.sub[1]) || void 0 === Dt || null === (Tt = Dt.sub[0]) || void 0 === Tt || null === (Pt = Tt.sub[0]) || void 0 === Pt || null === (Ft = Pt.sub[1]) || void 0 === Ft ? void 0 : Ft.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[1].header, s.sub[0].sub[1].sub[0].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[1].header + s.sub[0].sub[1].sub[0].sub[0].sub[1].length),
                                            Vid: null === (Rt = s.sub[0]) || void 0 === Rt || null === (Et = Rt.sub[1]) || void 0 === Et || null === (Lt = Et.sub[0]) || void 0 === Lt || null === (Vt = Lt.sub[0]) || void 0 === Vt || null === (Mt = Vt.sub[2]) || void 0 === Mt ? void 0 : Mt.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[0].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[2].header, s.sub[0].sub[1].sub[0].sub[0].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[0].sub[2].header + s.sub[0].sub[1].sub[0].sub[0].sub[2].length)
                                        },
                                        esID: null === (Nt = s.sub[0]) || void 0 === Nt || null === (Ht = Nt.sub[1]) || void 0 === Ht || null === (qt = Ht.sub[0]) || void 0 === qt || null === (Ut = qt.sub[1]) || void 0 === Ut ? void 0 : Ut.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[1].header, s.sub[0].sub[1].sub[0].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[1].header + s.sub[0].sub[1].sub[0].sub[1].length),
                                        property: {
                                            type: null === (Wt = s.sub[0]) || void 0 === Wt || null === ($t = Wt.sub[1]) || void 0 === $t || null === (zt = $t.sub[0]) || void 0 === zt || null === (Gt = zt.sub[2]) || void 0 === Gt || null === (Jt = Gt.sub[0]) || void 0 === Jt ? void 0 : Jt.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[2].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[0].header, s.sub[0].sub[1].sub[0].sub[2].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[0].header + s.sub[0].sub[1].sub[0].sub[2].sub[0].length),
                                            name: null === (Kt = s.sub[0]) || void 0 === Kt || null === (Xt = Kt.sub[1]) || void 0 === Xt || null === (Yt = Xt.sub[0]) || void 0 === Yt || null === (Qt = Yt.sub[2]) || void 0 === Qt || null === (Zt = Qt.sub[1]) || void 0 === Zt ? void 0 : Zt.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[2].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[1].header, s.sub[0].sub[1].sub[0].sub[2].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[1].header + s.sub[0].sub[1].sub[0].sub[2].sub[1].length),
                                            certListType: ns,
                                            certList: as,
                                            createDate: null === (en = s.sub[0]) || void 0 === en || null === (tn = en.sub[1]) || void 0 === tn || null === (nn = tn.sub[0]) || void 0 === nn || null === (sn = nn.sub[2]) || void 0 === sn || null === (an = sn.sub[4]) || void 0 === an ? void 0 : an.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[4].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[4].header, s.sub[0].sub[1].sub[0].sub[2].sub[4].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[4].header + s.sub[0].sub[1].sub[0].sub[2].sub[4].length),
                                            validStart: null === (rn = s.sub[0]) || void 0 === rn || null === (on = rn.sub[1]) || void 0 === on || null === (un = on.sub[0]) || void 0 === un || null === (cn = un.sub[2]) || void 0 === cn || null === (ln = cn.sub[5]) || void 0 === ln ? void 0 : ln.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[5].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[5].header, s.sub[0].sub[1].sub[0].sub[2].sub[5].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[5].header + s.sub[0].sub[1].sub[0].sub[2].sub[5].length),
                                            validEnd: null === (bn = s.sub[0]) || void 0 === bn || null === (dn = bn.sub[1]) || void 0 === dn || null === (fn = dn.sub[0]) || void 0 === fn || null === (hn = fn.sub[2]) || void 0 === hn || null === (pn = hn.sub[6]) || void 0 === pn ? void 0 : pn.stream.parseTime(s.sub[0].sub[1].sub[0].sub[2].sub[6].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[6].header, s.sub[0].sub[1].sub[0].sub[2].sub[6].stream.pos + s.sub[0].sub[1].sub[0].sub[2].sub[6].header + s.sub[0].sub[1].sub[0].sub[2].sub[6].length)
                                        },
                                        picture: {
                                            type: null === (vn = s.sub[0]) || void 0 === vn || null === (mn = vn.sub[1]) || void 0 === mn || null === (gn = mn.sub[0]) || void 0 === gn || null === (yn = gn.sub[3]) || void 0 === yn || null === (xn = yn.sub[0]) || void 0 === xn ? void 0 : xn.stream.parseStringUTF(s.sub[0].sub[1].sub[0].sub[3].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[0].header, s.sub[0].sub[1].sub[0].sub[3].sub[0].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[0].header + s.sub[0].sub[1].sub[0].sub[3].sub[0].length),
                                            data: {
                                                hex: null === (wn = s.sub[0]) || void 0 === wn || null === (On = wn.sub[1]) || void 0 === On || null === (Sn = On.sub[0]) || void 0 === Sn || null === (jn = Sn.sub[3]) || void 0 === jn || null === (kn = jn.sub[1]) || void 0 === kn ? void 0 : kn.stream.parseOctetString(s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header, s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header + s.sub[0].sub[1].sub[0].sub[3].sub[1].length),
                                                byte: null === (In = s.sub[0]) || void 0 === In || null === (Cn = In.sub[1]) || void 0 === Cn || null === (_n = Cn.sub[0]) || void 0 === _n || null === (An = _n.sub[3]) || void 0 === An || null === (Bn = An.sub[1]) || void 0 === Bn ? void 0 : Bn.stream.enc.subarray(s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header, s.sub[0].sub[1].sub[0].sub[3].sub[1].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[1].header + s.sub[0].sub[1].sub[0].sub[3].sub[1].length)
                                            },
                                            width: null === (Dn = s.sub[0]) || void 0 === Dn || null === (Tn = Dn.sub[1]) || void 0 === Tn || null === (Pn = Tn.sub[0]) || void 0 === Pn || null === (Fn = Pn.sub[3]) || void 0 === Fn || null === (Rn = Fn.sub[2]) || void 0 === Rn ? void 0 : Rn.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[3].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[2].header, s.sub[0].sub[1].sub[0].sub[3].sub[2].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[2].header + s.sub[0].sub[1].sub[0].sub[3].sub[2].length),
                                            height: null === (En = s.sub[0]) || void 0 === En || null === (Ln = En.sub[1]) || void 0 === Ln || null === (Vn = Ln.sub[0]) || void 0 === Vn || null === (Mn = Vn.sub[3]) || void 0 === Mn || null === (Nn = Mn.sub[3]) || void 0 === Nn ? void 0 : Nn.stream.parseInteger(s.sub[0].sub[1].sub[0].sub[3].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[3].header, s.sub[0].sub[1].sub[0].sub[3].sub[3].stream.pos + s.sub[0].sub[1].sub[0].sub[3].sub[3].header + s.sub[0].sub[1].sub[0].sub[3].sub[3].length)
                                        },
                                        extDatas: is
                                    },
                                    cert: z(null === (Hn = s.sub[0]) || void 0 === Hn || null === (qn = Hn.sub[1]) || void 0 === qn ? void 0 : qn.sub[1]),
                                    signAlgID: null === (Un = s.sub[0]) || void 0 === Un || null === (Wn = Un.sub[1]) || void 0 === Wn || null === ($n = Wn.sub[2]) || void 0 === $n ? void 0 : $n.stream.parseOID(s.sub[0].sub[1].sub[2].stream.pos + s.sub[0].sub[1].sub[2].header, s.sub[0].sub[1].sub[2].stream.pos + s.sub[0].sub[1].sub[2].header + s.sub[0].sub[1].sub[2].length),
                                    signedValue: null === (zn = s.sub[0]) || void 0 === zn || null === (Gn = zn.sub[1]) || void 0 === Gn || null === (Jn = Gn.sub[3]) || void 0 === Jn ? void 0 : Jn.stream.hexDump(s.sub[0].sub[1].sub[3].stream.pos + s.sub[0].sub[1].sub[3].header, s.sub[0].sub[1].sub[3].stream.pos + s.sub[0].sub[1].sub[3].header + s.sub[0].sub[1].sub[3].length, !1)
                                },
                                timeInfo: null === (Kn = s.sub[0]) || void 0 === Kn || null === (Xn = Kn.sub[2]) || void 0 === Xn ? void 0 : Xn.stream.parseTime(s.sub[0].sub[2].stream.pos + s.sub[0].sub[2].header, s.sub[0].sub[2].stream.pos + s.sub[0].sub[2].header + s.sub[0].sub[2].length, !1),
                                dataHash: null === (Yn = s.sub[0]) || void 0 === Yn || null === (Qn = Yn.sub[3]) || void 0 === Qn ? void 0 : Qn.stream.hexDump(s.sub[0].sub[3].stream.pos + s.sub[0].sub[3].header, s.sub[0].sub[3].stream.pos + s.sub[0].sub[3].header + s.sub[0].sub[3].length, !1),
                                propertyInfo: G(s.sub[0].sub[4])
                            },
                            cert: z(s.sub[1]),
                            signatureAlgID: null === (Zn = s.sub[2]) || void 0 === Zn ? void 0 : Zn.stream.parseOID(s.sub[2].stream.pos + s.sub[2].header, s.sub[2].stream.pos + s.sub[2].header + s.sub[2].length),
                            signature: null === (es = s.sub[3]) || void 0 === es ? void 0 : es.stream.hexDump(s.sub[3].stream.pos + s.sub[3].header, s.sub[3].stream.pos + s.sub[3].header + s.sub[3].length, !1),
                            timpStamp: null === (ts = s.sub[4]) || void 0 === ts ? void 0 : ts.stream.parseTime(s.sub[4].stream.pos + s.sub[4].header, s.sub[4].stream.pos + s.sub[4].header + s.sub[4].length)
                        }
                    } catch (os) {
                        console.log(os), n = {}
                    }
                }
                return n
            }, z = function (e, t) {
                t = t || 0;
                try {
                    var n, s, a = e.sub[0].sub[0].sub[5], r = new Map;
                    a.sub.forEach((function (e) {
                        if (null != e && "" != e && void 0 != e) {
                            var t, n = e.sub[0].sub[0].content().split("\n")[0],
                                s = null === (t = e.sub[0].sub[1]) || void 0 === t ? void 0 : t.stream.parseStringUTF(e.sub[0].sub[1].stream.pos + e.sub[0].sub[1].header, e.sub[0].sub[1].stream.pos + e.sub[0].sub[1].header + e.sub[0].sub[1].length);
                            r.set(n, s)
                        }
                    }));
                    var i = e.sub[0].sub[0].sub[6];
                    return {
                        subject: r,
                        commonName: r.get("2.5.4.3"),
                        subjectPublicKeyInfo: {
                            algorithm: null === (n = i.sub[0]) || void 0 === n ? void 0 : n.stream.parseOID(i.sub[0].stream.pos + i.sub[0].header, i.sub[0].stream.pos + i.sub[0].header + i.sub[0].length),
                            subjectPublicKey: null === (s = i.sub[1]) || void 0 === s ? void 0 : s.stream.hexDump(i.sub[1].stream.pos + i.sub[1].header, i.sub[1].stream.pos + i.sub[1].header + i.sub[1].length)
                        }
                    }
                } catch (o) {
                    return console.log(o), {}
                }
            }, G = function (e) {
                for (var t = "", n = 0; n < e.length; n++) t += String.fromCharCode(e[n]);
                return t
            }
    }, d00a: function (e, t, n) {
        "use strict";
        (function (e) {
            n.d(t, "a", (function () {
                return a
            }));
            var s = n("53ca"),
                a = "object" === ("undefined" === typeof e ? "undefined" : Object(s["a"])(e)) && e + "" === "[object process]" && !e.versions.nw && !(e.versions.electron && e.type && "browser" !== e.type)
        }).call(this, n("4362"))
    }
});