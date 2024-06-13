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
            <label class="form-control-label" for="response-output-transferCoreMapping">Transfer Core Mapping</label>
            <select
              class="form-control"
              name="transferCoreMapping"
              :class="{ valid: !v$.transferCoreMapping.$invalid, invalid: v$.transferCoreMapping.$invalid }"
              v-model="v$.transferCoreMapping.$model"
              id="response-output-transferCoreMapping"
              data-cy="transferCoreMapping"
            >
              <option v-for="coreTransferParams in coreTransferParamsValues" :key="coreTransferParams" v-bind:value="coreTransferParams">
                {{ coreTransferParams }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-isLogicField">Is Logic Field</label>
            <input
              type="checkbox"
              class="form-check"
              name="isLogicField"
              id="response-output-isLogicField"
              data-cy="isLogicField"
              :class="{ valid: !v$.isLogicField.$invalid, invalid: v$.isLogicField.$invalid }"
              v-model="v$.isLogicField.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-constantValueToCompare">Constant Value To Compare</label>
            <input
              type="text"
              class="form-control"
              name="constantValueToCompare"
              id="response-output-constantValueToCompare"
              data-cy="constantValueToCompare"
              :class="{ valid: !v$.constantValueToCompare.$invalid, invalid: v$.constantValueToCompare.$invalid }"
              v-model="v$.constantValueToCompare.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-operatorToCompareValue">Operator To Compare Value</label>
            <select
              class="form-control"
              name="operatorToCompareValue"
              :class="{ valid: !v$.operatorToCompareValue.$invalid, invalid: v$.operatorToCompareValue.$invalid }"
              v-model="v$.operatorToCompareValue.$model"
              id="response-output-operatorToCompareValue"
              data-cy="operatorToCompareValue"
            >
              <option v-for="logicalOperator in logicalOperatorValues" :key="logicalOperator" v-bind:value="logicalOperator">
                {{ logicalOperator }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="response-output-isRequired">Is Required</label>
            <input
              type="checkbox"
              class="form-check"
              name="isRequired"
              id="response-output-isRequired"
              data-cy="isRequired"
              :class="{ valid: !v$.isRequired.$invalid, invalid: v$.isRequired.$invalid }"
              v-model="v$.isRequired.$model"
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
