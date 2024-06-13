<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.requestInput.home.createOrEditLabel" data-cy="RequestInputCreateUpdateHeading">
          Create or edit a Request Input
        </h2>
        <div>
          <div class="form-group" v-if="requestInput.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="requestInput.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-inputName">Input Name</label>
            <input
              type="text"
              class="form-control"
              name="inputName"
              id="request-input-inputName"
              data-cy="inputName"
              :class="{ valid: !v$.inputName.$invalid, invalid: v$.inputName.$invalid }"
              v-model="v$.inputName.$model"
              required
            />
            <div v-if="v$.inputName.$anyDirty && v$.inputName.$invalid">
              <small class="form-text text-danger" v-for="error of v$.inputName.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-inputType">Input Type</label>
            <select
              class="form-control"
              name="inputType"
              :class="{ valid: !v$.inputType.$invalid, invalid: v$.inputType.$invalid }"
              v-model="v$.inputType.$model"
              id="request-input-inputType"
              data-cy="inputType"
              required
            >
              <option v-for="requestInputType in requestInputTypeValues" :key="requestInputType" v-bind:value="requestInputType">
                {{ requestInputType }}
              </option>
            </select>
            <div v-if="v$.inputType.$anyDirty && v$.inputType.$invalid">
              <small class="form-text text-danger" v-for="error of v$.inputType.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-dataType">Data Type</label>
            <select
              class="form-control"
              name="dataType"
              :class="{ valid: !v$.dataType.$invalid, invalid: v$.dataType.$invalid }"
              v-model="v$.dataType.$model"
              id="request-input-dataType"
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
            <label class="form-control-label" for="request-input-testValue">Test Value</label>
            <input
              type="text"
              class="form-control"
              name="testValue"
              id="request-input-testValue"
              data-cy="testValue"
              :class="{ valid: !v$.testValue.$invalid, invalid: v$.testValue.$invalid }"
              v-model="v$.testValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-defaultValue">Default Value</label>
            <input
              type="text"
              class="form-control"
              name="defaultValue"
              id="request-input-defaultValue"
              data-cy="defaultValue"
              :class="{ valid: !v$.defaultValue.$invalid, invalid: v$.defaultValue.$invalid }"
              v-model="v$.defaultValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-valueSource">Value Source</label>
            <select
              class="form-control"
              name="valueSource"
              :class="{ valid: !v$.valueSource.$invalid, invalid: v$.valueSource.$invalid }"
              v-model="v$.valueSource.$model"
              id="request-input-valueSource"
              data-cy="valueSource"
            >
              <option v-for="requestValueSource in requestValueSourceValues" :key="requestValueSource" v-bind:value="requestValueSource">
                {{ requestValueSource }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-isEncoded">Is Encoded</label>
            <input
              type="checkbox"
              class="form-check"
              name="isEncoded"
              id="request-input-isEncoded"
              data-cy="isEncoded"
              :class="{ valid: !v$.isEncoded.$invalid, invalid: v$.isEncoded.$invalid }"
              v-model="v$.isEncoded.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-maxLength">Max Length</label>
            <input
              type="number"
              class="form-control"
              name="maxLength"
              id="request-input-maxLength"
              data-cy="maxLength"
              :class="{ valid: !v$.maxLength.$invalid, invalid: v$.maxLength.$invalid }"
              v-model.number="v$.maxLength.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-minLength">Min Length</label>
            <input
              type="number"
              class="form-control"
              name="minLength"
              id="request-input-minLength"
              data-cy="minLength"
              :class="{ valid: !v$.minLength.$invalid, invalid: v$.minLength.$invalid }"
              v-model.number="v$.minLength.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-minValue">Min Value</label>
            <input
              type="text"
              class="form-control"
              name="minValue"
              id="request-input-minValue"
              data-cy="minValue"
              :class="{ valid: !v$.minValue.$invalid, invalid: v$.minValue.$invalid }"
              v-model="v$.minValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-maxValue">Max Value</label>
            <input
              type="text"
              class="form-control"
              name="maxValue"
              id="request-input-maxValue"
              data-cy="maxValue"
              :class="{ valid: !v$.maxValue.$invalid, invalid: v$.maxValue.$invalid }"
              v-model="v$.maxValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-validationPattern">Validation Pattern</label>
            <input
              type="text"
              class="form-control"
              name="validationPattern"
              id="request-input-validationPattern"
              data-cy="validationPattern"
              :class="{ valid: !v$.validationPattern.$invalid, invalid: v$.validationPattern.$invalid }"
              v-model="v$.validationPattern.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-isRequired">Is Required</label>
            <input
              type="checkbox"
              class="form-check"
              name="isRequired"
              id="request-input-isRequired"
              data-cy="isRequired"
              :class="{ valid: !v$.isRequired.$invalid, invalid: v$.isRequired.$invalid }"
              v-model="v$.isRequired.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="request-input-operation">Operation</label>
            <select class="form-control" id="request-input-operation" data-cy="operation" name="operation" v-model="requestInput.operation">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  requestInput.operation && operationOption.id === requestInput.operation.id ? requestInput.operation : operationOption
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
<script lang="ts" src="./request-input-update.component.ts"></script>
