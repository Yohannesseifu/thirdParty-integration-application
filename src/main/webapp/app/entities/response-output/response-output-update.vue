<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.responseOutput.home.createOrEditLabel" data-cy="ResponseOutputCreateUpdateHeading">
          Create or edit a Response Output
        </h2>
        <div>
          <div class="form-group" v-if="responseOutput.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="responseOutput.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-outputName">Output Name</label>
            <input
              type="text"
              class="form-control"
              name="outputName"
              id="response-output-outputName"
              data-cy="outputName"
              :class="{ valid: !v$.outputName.$invalid, invalid: v$.outputName.$invalid }"
              v-model="v$.outputName.$model"
              required
            />
            <div v-if="v$.outputName.$anyDirty && v$.outputName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.outputName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-dataType">Data Type</label>
            <select
              class="form-control"
              name="dataType"
              :class="{ valid: !v$.dataType.$invalid, invalid: v$.dataType.$invalid }"
              v-model="v$.dataType.$model"
              id="response-output-dataType"
              data-cy="dataType"
              required
            >
              <option v-for="dataType in dataTypeValues" :key="dataType" v-bind:value="dataType">{{ dataType }}</option>
            </select>
            <div v-if="v$.dataType.$anyDirty && v$.dataType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.dataType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-responseValuePath">Response Value Path</label>
            <input
              type="text"
              class="form-control"
              name="responseValuePath"
              id="response-output-responseValuePath"
              data-cy="responseValuePath"
              :class="{ valid: !v$.responseValuePath.$invalid, invalid: v$.responseValuePath.$invalid }"
              v-model="v$.responseValuePath.$model"
              required
            />
            <div v-if="v$.responseValuePath.$anyDirty && v$.responseValuePath.$invalid">
              <small class="form-text text-danger" v-for="error of v$.responseValuePath.$errors" :key="error.$uid">{{
                error.$message
              }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-responseScope">Response Scope</label>
            <select
              class="form-control"
              name="responseScope"
              :class="{ valid: !v$.responseScope.$invalid, invalid: v$.responseScope.$invalid }"
              v-model="v$.responseScope.$model"
              id="response-output-responseScope"
              data-cy="responseScope"
              required
            >
              <option v-for="scope in scopeValues" :key="scope" v-bind:value="scope">{{ scope }}</option>
            </select>
            <div v-if="v$.responseScope.$anyDirty && v$.responseScope.$invalid">
              <small class="form-text text-danger" v-for="error of v$.responseScope.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-coreMapping">Core Mapping</label>
            <select
              class="form-control"
              name="coreMapping"
              :class="{ valid: !v$.coreMapping.$invalid, invalid: v$.coreMapping.$invalid }"
              v-model="v$.coreMapping.$model"
              id="response-output-coreMapping"
              data-cy="coreMapping"
            >
              <option v-for="coreMapping in coreMappingValues" :key="coreMapping" v-bind:value="coreMapping">{{ coreMapping }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-expectedValue">Expected Value</label>
            <input
              type="text"
              class="form-control"
              name="expectedValue"
              id="response-output-expectedValue"
              data-cy="expectedValue"
              :class="{ valid: !v$.expectedValue.$invalid, invalid: v$.expectedValue.$invalid }"
              v-model="v$.expectedValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-operation">Operation</label>
            <select
              class="form-control"
              id="response-output-operation"
              data-cy="operation"
              name="operation"
              v-model="responseOutput.operation"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  responseOutput.operation && operationOption.id === responseOutput.operation.id
                    ? responseOutput.operation
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
<script lang="ts" src="./response-output-update.component.ts"></script>
