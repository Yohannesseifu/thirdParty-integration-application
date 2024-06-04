<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="thirdPartyIntegrationApplicationApp.integrationOperation.home.createOrEditLabel"
          data-cy="IntegrationOperationCreateUpdateHeading"
        >
          Create or edit a Integration Operation
        </h2>
        <div>
          <div class="form-group" v-if="integrationOperation.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="integrationOperation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-operation-operationType">Operation Type</label>
            <select
              class="form-control"
              name="operationType"
              :class="{ valid: !v$.operationType.$invalid, invalid: v$.operationType.$invalid }"
              v-model="v$.operationType.$model"
              id="integration-operation-operationType"
              data-cy="operationType"
            >
              <option v-for="operationType in operationTypeValues" :key="operationType" v-bind:value="operationType">
                {{ operationType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-operation-thirdPartyIntegration">Third Party Integration</label>
            <select
              class="form-control"
              id="integration-operation-thirdPartyIntegration"
              data-cy="thirdPartyIntegration"
              name="thirdPartyIntegration"
              v-model="integrationOperation.thirdPartyIntegration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  integrationOperation.thirdPartyIntegration &&
                  thirdPartyIntegrationOption.id === integrationOperation.thirdPartyIntegration.id
                    ? integrationOperation.thirdPartyIntegration
                    : thirdPartyIntegrationOption
                "
                v-for="thirdPartyIntegrationOption in thirdPartyIntegrations"
                :key="thirdPartyIntegrationOption.id"
              >
                {{ thirdPartyIntegrationOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="integration-operation-operation">Operation</label>
            <select
              class="form-control"
              id="integration-operation-operation"
              data-cy="operation"
              name="operation"
              v-model="integrationOperation.operation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  integrationOperation.operation && operationOption.id === integrationOperation.operation.id
                    ? integrationOperation.operation
                    : operationOption
                "
                v-for="operationOption in operations"
                :key="operationOption.id"
              >
                {{ operationOption.id }}
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
<script lang="ts" src="./integration-operation-update.component.ts"></script>
