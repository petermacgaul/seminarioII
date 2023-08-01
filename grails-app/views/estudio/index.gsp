<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'estudio.label', default: 'Estudio')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-estudio" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/medico')}"><g:message code="Medicos"/></a></li>
                        <li><g:link class="create" action="create" params="[turnoId: turno.id]"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-estudio" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <table>
                        <thead>
                        <tr>
                            <th>Turno</th>
                            <th>Paciente</th>
                            <th>Tipo</th>
                            <th>Precio</th>
                        </tr>
                        </thead>

                        <tbody>
                        <g:each var="estudio" in="${estudioList}">

                            <tr>
                                <td>${estudio.turno}</td>
                                <td>${estudio.paciente}</td>
                                <td>${estudio.tipo}</td>
                                <td>${estudio.precio}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                    <g:if test="${estudioCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${estudioCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>