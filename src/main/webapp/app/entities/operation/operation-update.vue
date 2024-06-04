<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.operation.home.createOrEditLabel" data-cy="OperationCreateUpdateHeading">
          Create or edit a Operation
        </h2>
        <div>
          <div class="form-group" v-if="operation.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="operation.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="operation-operationName">Operation Name</label>
            <input
              type="text"
              class="form-control"
              name="operationName"
              id="operation-operationName"
              data-cy="operationName"
              :class="{ valid: !v$.operationName.$invalid, invalid: v$.operationName.$invalid }"
              v-model="v$.operationName.$model"
              required
            />
            <div v-if="v$.operationName.$anyDirty && v$.operationName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.operationName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="operation-httpMethod">Http Method</label>
            <select
              class="form-control"
              name="httpMethod"
              :class="{ valid: !v$.httpMethod.$invalid, invalid: v$.httpMethod.$invalid }"
              v-model="v$.httpMethod.$model"
              id="operation-httpMethod"
              data-cy="httpMethod"
              required
            >
              <option v-for="httpMethod in httpMethodValues" :key="httpMethod" v-bind:value="httpMethod">{{ httpMethod }}</option>
            </select>
            <div v-if="v$.httpMethod.$anyDirty && v$.httpMethod.$invalid">
              <small class="form-text text-danger" v-for="error of v$.httpMethod.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="operation-endpointPath">Endpoint Path</label>
            <input
              type="text"
              class="form-control"
              name="endpointPath"
              id="operation-endpointPath"
              data-cy="endpointPath"
              :class="{ valid: !v$.endpointPath.$invalid, invalid: v$.endpointPath.$invalid }"
              v-model="v$.endpointPath.$model"
              required
            />
            <div v-if="v$.endpointPath.$anyDirty && v$.endpointPath.$invalid">
              <small class="form-text text-danger" v-for="error of v$.endpointPath.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="operation-requestBodyTemplate">Request Body Template</label>
            <input
              type="text"
              class="form-control"
              name="requestBodyTemplate"
              id="operation-requestBodyTemplate"
              data-cy="requestBodyTemplate"
              :class="{ valid: !v$.requestBodyTemplate.$invalid, invalid: v$.requestBodyTemplate.$invalid }"
              v-model="v$.requestBodyTemplate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="operation-apiIntegration">Api Integration</label>
            <select
              class="form-control"
              id="operation-apiIntegration"
              data-cy="apiIntegration"
              name="apiIntegration"
              v-model="operation.apiIntegration"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  operation.apiIntegration && apiIntegrationOption.id === operation.apiIntegration.id
                    ? operation.apiIntegration
                    : apiIntegrationOption
                "
                v-for="apiIntegrationOption in apiIntegrations"
                :key="apiIntegrationOption.id"
              >
                {{ apiIntegrationOption.id }}
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
<script lang="ts" src="./operation-update.component.ts"></script>
