<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'medico.label', default: 'Medico')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#create-medico" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="create-medico" class="col-12 content scaffold-create" role="main">
                    <h1><g:message code="default.create.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                    <div class="message" role="status">${flash.message}</div>
                    </g:if>
                    <g:hasErrors bean="${this.medico}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.medico}" var="error">
                        <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                    </g:hasErrors>
                    <g:form resource="${this.medico}" method="POST">
                        <fieldset class="form">
                            <g:set var="propertiesToDisplay" value="${['dni','nombre', 'apellido',  'matricula', 'especialidad']}" />

                            <g:each var="propertyKey" in="${propertiesToDisplay}">
                                <div class="fieldcontain">
                                    <label for="${propertyKey}"><g:message code="medico.${propertyKey}.label" default="${propertyKey == 'dni' ? propertyKey.toUpperCase(): propertyKey.capitalize()}" /></label>
                                    <g:textField name="${propertyKey}" value="${this.medico[propertyKey]}" />
                                </div>
                            </g:each>
                        </fieldset>
                        <fieldset class="buttons">
                            <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                        </fieldset>
                    </g:form>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>
