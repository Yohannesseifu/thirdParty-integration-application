<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="thirdPartyIntegrationApplicationApp.integrationFieldMapping.home.createOrEditLabel"
          data-cy="IntegrationFieldMappingCreateUpdateHeading"
        >
          Create or edit a Integration Field Mapping
        </h2>
        <div>
          <div class="form-group" v-if="integrationFieldMapping.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="integrationFieldMapping.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-field-mapping-integrationOperation">Integration Operation</label>
            <select
              class="form-control"
              id="integration-field-mapping-integrationOperation"
              data-cy="integrationOperation"
              name="integrationOperation"
              v-model="integrationFieldMapping.integrationOperation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  integrationFieldMapping.integrationOperation &&
                  integrationOperationOption.id === integrationFieldMapping.integrationOperation.id
                    ? integrationFieldMapping.integrationOperation
                    : integrationOperationOption
                "
                v-for="integrationOperationOption in integrationOperations"
                :key="integrationOperationOption.id"
              >
                {{ integrationOperationOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-field-mapping-field">Field</label>
            <select
              class="form-control"
              id="integration-field-mapping-field"
              data-cy="field"
              name="field"
              v-model="integrationFieldMapping.field"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  integrationFieldMapping.field && fieldOption.id === integrationFieldMapping.field.id
                    ? integrationFieldMapping.field
                    : fieldOption
                "
                v-for="fieldOption in fields"
                :key="fieldOption.id"
              >
                {{ fieldOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-field-mapping-requestInput">Request Input</label>
            <select
              class="form-control"
              id="integration-field-mapping-requestInput"
              data-cy="requestInput"
              name="requestInput"
              v-model="integrationFieldMapping.requestInput"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  integrationFieldMapping.requestInput && requestInputOption.id === integrationFieldMapping.requestInput.id
                    ? integrationFieldMapping.requestInput
                    : requestInputOption
                "
                v-for="requestInputOption in requestInputs"
                :key="requestInputOption.id"
              >
                {{ requestInputOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./integration-field-mapping-update.component.ts"></script>
