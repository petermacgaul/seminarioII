<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'turno.label', default: 'Turno')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-turno" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="list" href="${createLink(uri: '/paciente')}"><g:message code="Pacientes"/></a></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-turno" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="alert alert-success" role="alert">${flash.message}</div>
                    </g:if>
                    <g:if test="${flash.warning}">
                        <div class="alert alert-warning" role="alert">${flash.warning}</div>
                    </g:if>
                    <g:if test="${flash.error}">
                        <div class="alert alert-danger" role="alert">${flash.error}</div>
                    </g:if>
                    <table>
                        <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Medico</th>
                            <th>Lugar</th>
                            <th>Precio consulta</th>
                            <th>Precio total</th>
                            <th>Accion</th>
                        </tr>
                        </thead>

                        <tbody>
                        <g:each var="turno" in="${turnoList}">

                            <tr>
                                <td>${turno.fecha}</td>
                                <td>${turno.medico}</td>
                                <td>${turno.lugar}</td>
                                <td>${turno.precioConsulta}</td>
                                <td>

                                    <g:if test="${turno.paciente != null && turno.paciente.id == paciente.id}">
                                        ${turno.precioTotal}
                                    </g:if>
                                    <g:else>
                                        "N/A"
                                    </g:else>
                                <td>
                                    <g:if test="${turno.paciente == null}">
                                        <form method="post" action="${createLink(controller: 'turnoPaciente', action: 'reservarTurno')}">
                                            <input type="hidden" name="turnoId" value="${turno.id}" />
                                            <input type="hidden" name="pacienteId" value="${paciente.id}" />
                                            <button type="submit" class="btn btn-primary">Reservar</button>
                                        </form>
                                    </g:if>
                                    <g:else>
                                        <form method="post" action="${createLink(controller: 'turnoPaciente', action: 'cancelarTurno')}">
                                            <input type="hidden" name="turnoId" value="${turno.id}" />
                                            <input type="hidden" name="pacienteId" value="${paciente.id}" />
                                            <button type="submit" class="btn btn-danger">Cancelar</button>
                                        </form>
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                    <g:if test="${turnoCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${turnoCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>