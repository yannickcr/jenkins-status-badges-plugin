package org.jenkinsci.plugins.statusbadges.StatusAction

def l = namespace(lib.LayoutTagLib)
def st = namespace("jelly:stapler")

l.layout {
    l.main_panel {
        h2(_("Status Badges"))
        p(raw(_("blurb")))
        raw("""
<p>
</p>
<script>
    Behaviour.register({
        "INPUT.select-all" : function(e) {
            e.onclick = function () {
                e.focus();
                e.select();
            }
        }
    });
</script>
<style>
    INPUT.select-all {
        width:100%;
    }
    IMG#badge {
        margin-left:2em;
    }
    .codes {
        border-collapse: collapse;
        width: 100%;
    }
    .codes td {
        vertical-align: middle;
        padding: 10px;
        border: 1px solid #CCC;
    }
    .codes img {
        margin: auto;
        display: block;
    }
</style>
""")

        def base =  "${app.rootUrl}${my.project.url}";
        def fullJobName = h.escape(my.project.fullName);

        def badgeBuild = base + "statusbadges-build/icon"
        def publicbadgeBuild = "${app.rootUrl}statusbadges-build/icon?job=${fullJobName}";
        def badgeCoverage = base + "statusbadges-coverage/icon"
        def publicbadgeCoverage = "${app.rootUrl}statusbadges-coverage/icon?job=${fullJobName}";
        def badgeGrade = base + "statusbadges-grade/icon"
        def publicbadgeGrade = "${app.rootUrl}statusbadges-grade/icon?job=${fullJobName}";
        def badgeServer = base + "statusbadges-server/icon"
        def publicbadgeServer = "${app.rootUrl}statusbadges-server/icon?job=${fullJobName}";

        table(class:"codes") {
            thead {
                tr {
                    th {
                        text(_("Preview"))
                    }
                    th {
                        text(_("Code"))
                    }
                }
            }
            tbody {
                tr {
                    td {
                        img(src:publicbadgeBuild)
                    }
                    td {
                        b {text(_("Protected"))}
                        input(type:"text",value:badgeBuild,class:"select-all")
                        b {text(_("Unprotected"))}
                        input(type:"text",value:publicbadgeBuild,class:"select-all")
                    }
                }
                tr {
                    td {
                        img(src:publicbadgeGrade)
                    }
                    td {
                        b {text(_("Protected"))}
                        input(type:"text",value:badgeGrade,class:"select-all")
                        b {text(_("Unprotected"))}
                        input(type:"text",value:publicbadgeGrade,class:"select-all")
                    }
                }
                tr {
                    td {
                        img(src:publicbadgeCoverage)
                    }
                    td {
                        b {text(_("Protected"))}
                        input(type:"text",value:badgeCoverage,class:"select-all")
                        b {text(_("Unprotected"))}
                        input(type:"text",value:publicbadgeCoverage,class:"select-all")
                    }
                }
                tr {
                    td {
                        img(src:publicbadgeServer)
                    }
                    td {
                        b {text(_("Protected"))}
                        input(type:"text",value:badgeServer,class:"select-all")
                        b {text(_("Unprotected"))}
                        input(type:"text",value:publicbadgeServer,class:"select-all")
                    }
                }
            }
        }
    }
}
