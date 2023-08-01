<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'paciente.label', default: 'Paciente')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-paciente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-paciente" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>

                    <table>
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>DNI</th>
                                <th>Email</th>
                                <th>Fecha De Nacimiento</th>
                                <th>Cobertura</th>
                                <th>Accion</th>
                            </tr>
                        </thead>

                        <tbody>
                            <g:each var="paciente" in="${pacienteList}">

                                <tr>
                                    <td>${paciente.nombre} ${paciente.apellido}</td>
                                    <td>${paciente.dni}</td>
                                    <td>${paciente.email}</td>
                                    <td>${paciente.fechaDeNacimiento}</td>
                                    <td>${paciente.cobertura}</td>

                                    <td>
                                        <a href="${createLink(controller: 'turnoPaciente', action: 'index', id: paciente.id)}" style="text-decoration: none;" class="btn btn-primary">Turnos</a>
                                    </td>
                                </tr>
                            </g:each>
                        </tbody>
                    </table>

                    <g:if test="${pacienteCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${pacienteCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>