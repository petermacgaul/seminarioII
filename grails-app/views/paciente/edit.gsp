<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'paciente.label', default: 'Paciente')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#edit-paciente" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="edit-paciente" class="col-12 content scaffold-edit" role="main">
                    <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.paciente}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.paciente}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                    <g:form resource="${this.paciente}" method="PUT">
                        <g:hiddenField name="version" value="${this.paciente?.version}" />
                        <fieldset class="form">
<g:set var="propertiesToDisplay" value="${['dni', 'nombre', 'apellido', 'email', 'fechaDeNacimiento']}" />

                            <g:each var="propertyKey" in="${propertiesToDisplay}">
                                <div class="fieldcontain">
                                    <label for="${propertyKey}">
                                        <g:message code="paciente.${propertyKey}.label"
                                            default="${propertyKey == 'dni' ? propertyKey.toUpperCase() : propertyKey == 'fechaDeNacimiento' ? 'Fecha de nacimiento' : propertyKey.capitalize()}"/>
                                        <span class="required-indicator">*</span>
                                    </label>

                                    <g:if test="${propertyKey == 'fechaDeNacimiento'}">
                                        <!-- Calendario para seleccionar la fecha de nacimiento -->
                                        <input type="date" name="${propertyKey}" value="${this.paciente[propertyKey]}" />
                                    </g:if>

                                    <g:else>
                                        <!-- Texto normal para las otras propiedades -->
                                        <input type="text" name="${propertyKey}" value="${this.paciente[propertyKey]}"/>
                                    </g:else>
                                </div>
                            </g:each>

                            <f:all bean="paciente" except="['dni', 'nombre', 'apellido', 'email', 'fechaDeNacimiento', 'turnos']"/>
                            <f:all bean="paciente" except="['dni', 'nombre', 'apellido', 'email', 'fechaDeNacimiento', 'cobertura']"/>

                        <fieldset class="buttons">
                            <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>
