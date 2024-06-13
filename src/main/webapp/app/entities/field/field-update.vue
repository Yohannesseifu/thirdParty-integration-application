<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.field.home.createOrEditLabel" data-cy="FieldCreateUpdateHeading">
          Create or edit a Field
        </h2>
        <div>
          <div class="form-group" v-if="field.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="field.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="field-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-dataType">Data Type</label>
            <select
              class="form-control"
              name="dataType"
              :class="{ valid: !v$.dataType.$invalid, invalid: v$.dataType.$invalid }"
              v-model="v$.dataType.$model"
              id="field-dataType"
              data-cy="dataType"
            >
              <option v-for="dataType in dataTypeValues" :key="dataType" v-bind:value="dataType">{{ dataType }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-isUnique">Is Unique</label>
            <input
              type="checkbox"
              class="form-check"
              name="isUnique"
              id="field-isUnique"
              data-cy="isUnique"
              :class="{ valid: !v$.isUnique.$invalid, invalid: v$.isUnique.$invalid }"
              v-model="v$.isUnique.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-maxLength">Max Length</label>
            <input
              type="number"
              class="form-control"
              name="maxLength"
              id="field-maxLength"
              data-cy="maxLength"
              :class="{ valid: !v$.maxLength.$invalid, invalid: v$.maxLength.$invalid }"
              v-model.number="v$.maxLength.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-minLength">Min Length</label>
            <input
              type="number"
              class="form-control"
              name="minLength"
              id="field-minLength"
              data-cy="minLength"
              :class="{ valid: !v$.minLength.$invalid, invalid: v$.minLength.$invalid }"
              v-model.number="v$.minLength.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-minValue">Min Value</label>
            <input
              type="text"
              class="form-control"
              name="minValue"
              id="field-minValue"
              data-cy="minValue"
              :class="{ valid: !v$.minValue.$invalid, invalid: v$.minValue.$invalid }"
              v-model="v$.minValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-maxValue">Max Value</label>
            <input
              type="text"
              class="form-control"
              name="maxValue"
              id="field-maxValue"
              data-cy="maxValue"
              :class="{ valid: !v$.maxValue.$invalid, invalid: v$.maxValue.$invalid }"
              v-model="v$.maxValue.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-isRequired">Is Required</label>
            <input
              type="checkbox"
              class="form-check"
              name="isRequired"
              id="field-isRequired"
              data-cy="isRequired"
              :class="{ valid: !v$.isRequired.$invalid, invalid: v$.isRequired.$invalid }"
              v-model="v$.isRequired.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-transferCoreMapping">Transfer Core Mapping</label>
            <select
              class="form-control"
              name="transferCoreMapping"
              :class="{ valid: !v$.transferCoreMapping.$invalid, invalid: v$.transferCoreMapping.$invalid }"
              v-model="v$.transferCoreMapping.$model"
              id="field-transferCoreMapping"
              data-cy="transferCoreMapping"
            >
              <option v-for="coreTransferParams in coreTransferParamsValues" :key="coreTransferParams" v-bind:value="coreTransferParams">
                {{ coreTransferParams }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-sortOrder">Sort Order</label>
            <input
              type="number"
              class="form-control"
              name="sortOrder"
              id="field-sortOrder"
              data-cy="sortOrder"
              :class="{ valid: !v$.sortOrder.$invalid, invalid: v$.sortOrder.$invalid }"
              v-model.number="v$.sortOrder.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-formUi">Form Ui</label>
            <select class="form-control" id="field-formUi" data-cy="formUi" name="formUi" v-model="field.formUi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="field.formUi && formUiOption.id === field.formUi.id ? field.formUi : formUiOption"
                v-for="formUiOption in formUis"
                :key="formUiOption.id"
              >
                {{ formUiOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="field-fieldUIMetaData">Field UI Meta Data</label>
            <select
              class="form-control"
              id="field-fieldUIMetaData"
              data-cy="fieldUIMetaData"
              name="fieldUIMetaData"
              v-model="field.fieldUIMetaData"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  field.fieldUIMetaData && fieldUIMetaDataOption.id === field.fieldUIMetaData.id
                    ? field.fieldUIMetaData
                    : fieldUIMetaDataOption
                "
                v-for="fieldUIMetaDataOption in fieldUIMetaData"
                :key="fieldUIMetaDataOption.id"
              >
                {{ fieldUIMetaDataOption.id }}
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
<script lang="ts" src="./field-update.component.ts"></script>
