<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'medico.label', default: 'Medico')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-medico" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="list" href="${createLink(uri: '/medico/turnos')}"><g:message code="Listado de turnos"/></a></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-medico" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>

                    <table>
                        <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Especialidad</th>
                            <th>DNI</th>
                            <th>Matricula</th>
                            <th>Accion</th>
                        </tr>
                        </thead>

                        <tbody>
                        <g:each var="medico" in="${medicoList}">

                            <tr>
                                <td>${medico.nombre} ${medico.apellido}</td>
                                <td>${medico.especialidad}</td>
                                <td>${medico.dni}</td>
                                <td>${medico.matricula}</td>
                                <td>
                                    <a href="${createLink(controller: 'turno', action: 'index', id: medico.id)}" style="text-decoration: none;" class="btn btn-primary">Turnos</a>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${medicoCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${medicoCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>