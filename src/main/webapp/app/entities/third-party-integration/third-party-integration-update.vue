<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2
          id="thirdPartyIntegrationApplicationApp.thirdPartyIntegration.home.createOrEditLabel"
          data-cy="ThirdPartyIntegrationCreateUpdateHeading"
        >
          Create or edit a Third Party Integration
        </h2>
        <div>
          <div class="form-group" v-if="thirdPartyIntegration.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="thirdPartyIntegration.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-isDraft">Is Draft</label>
            <input
              type="checkbox"
              class="form-check"
              name="isDraft"
              id="third-party-integration-isDraft"
              data-cy="isDraft"
              :class="{ valid: !v$.isDraft.$invalid, invalid: v$.isDraft.$invalid }"
              v-model="v$.isDraft.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-integrationName">Integration Name</label>
            <input
              type="text"
              class="form-control"
              name="integrationName"
              id="third-party-integration-integrationName"
              data-cy="integrationName"
              :class="{ valid: !v$.integrationName.$invalid, invalid: v$.integrationName.$invalid }"
              v-model="v$.integrationName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-companyName">Company Name</label>
            <input
              type="text"
              class="form-control"
              name="companyName"
              id="third-party-integration-companyName"
              data-cy="companyName"
              :class="{ valid: !v$.companyName.$invalid, invalid: v$.companyName.$invalid }"
              v-model="v$.companyName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="third-party-integration-description"
              data-cy="description"
              :class="{ valid: !v$.description.$invalid, invalid: v$.description.$invalid }"
              v-model="v$.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-iconPath">Icon Path</label>
            <input
              type="text"
              class="form-control"
              name="iconPath"
              id="third-party-integration-iconPath"
              data-cy="iconPath"
              :class="{ valid: !v$.iconPath.$invalid, invalid: v$.iconPath.$invalid }"
              v-model="v$.iconPath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-enabled">Enabled</label>
            <input
              type="checkbox"
              class="form-check"
              name="enabled"
              id="third-party-integration-enabled"
              data-cy="enabled"
              :class="{ valid: !v$.enabled.$invalid, invalid: v$.enabled.$invalid }"
              v-model="v$.enabled.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-accountNumber">Account Number</label>
            <input
              type="text"
              class="form-control"
              name="accountNumber"
              id="third-party-integration-accountNumber"
              data-cy="accountNumber"
              :class="{ valid: !v$.accountNumber.$invalid, invalid: v$.accountNumber.$invalid }"
              v-model="v$.accountNumber.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-minimumAmount">Minimum Amount</label>
            <input
              type="number"
              class="form-control"
              name="minimumAmount"
              id="third-party-integration-minimumAmount"
              data-cy="minimumAmount"
              :class="{ valid: !v$.minimumAmount.$invalid, invalid: v$.minimumAmount.$invalid }"
              v-model.number="v$.minimumAmount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-maximumAmount">Maximum Amount</label>
            <input
              type="number"
              class="form-control"
              name="maximumAmount"
              id="third-party-integration-maximumAmount"
              data-cy="maximumAmount"
              :class="{ valid: !v$.maximumAmount.$invalid, invalid: v$.maximumAmount.$invalid }"
              v-model.number="v$.maximumAmount.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-currencyCode">Currency Code</label>
            <input
              type="text"
              class="form-control"
              name="currencyCode"
              id="third-party-integration-currencyCode"
              data-cy="currencyCode"
              :class="{ valid: !v$.currencyCode.$invalid, invalid: v$.currencyCode.$invalid }"
              v-model="v$.currencyCode.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-paymentConfirmationTemplate"
              >Payment Confirmation Template</label
            >
            <input
              type="text"
              class="form-control"
              name="paymentConfirmationTemplate"
              id="third-party-integration-paymentConfirmationTemplate"
              data-cy="paymentConfirmationTemplate"
              :class="{ valid: !v$.paymentConfirmationTemplate.$invalid, invalid: v$.paymentConfirmationTemplate.$invalid }"
              v-model="v$.paymentConfirmationTemplate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-paymentSuccessTemplate">Payment Success Template</label>
            <input
              type="text"
              class="form-control"
              name="paymentSuccessTemplate"
              id="third-party-integration-paymentSuccessTemplate"
              data-cy="paymentSuccessTemplate"
              :class="{ valid: !v$.paymentSuccessTemplate.$invalid, invalid: v$.paymentSuccessTemplate.$invalid }"
              v-model="v$.paymentSuccessTemplate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-paymentErrorTemplate">Payment Error Template</label>
            <input
              type="text"
              class="form-control"
              name="paymentErrorTemplate"
              id="third-party-integration-paymentErrorTemplate"
              data-cy="paymentErrorTemplate"
              :class="{ valid: !v$.paymentErrorTemplate.$invalid, invalid: v$.paymentErrorTemplate.$invalid }"
              v-model="v$.paymentErrorTemplate.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-integrationCategory">Integration Category</label>
            <select
              class="form-control"
              name="integrationCategory"
              :class="{ valid: !v$.integrationCategory.$invalid, invalid: v$.integrationCategory.$invalid }"
              v-model="v$.integrationCategory.$model"
              id="third-party-integration-integrationCategory"
              data-cy="integrationCategory"
            >
              <option v-for="integrationType in integrationTypeValues" :key="integrationType" v-bind:value="integrationType">
                {{ integrationType }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-visiblity">Visiblity</label>
            <select
              class="form-control"
              name="visiblity"
              :class="{ valid: !v$.visiblity.$invalid, invalid: v$.visiblity.$invalid }"
              v-model="v$.visiblity.$model"
              id="third-party-integration-visiblity"
              data-cy="visiblity"
            >
              <option v-for="visiblity in visiblityValues" :key="visiblity" v-bind:value="visiblity">{{ visiblity }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="third-party-integration-confirmRecipientIdentity">Confirm Recipient Identity</label>
            <input
              type="checkbox"
              class="form-check"
              name="confirmRecipientIdentity"
              id="third-party-integration-confirmRecipientIdentity"
              data-cy="confirmRecipientIdentity"
              :class="{ valid: !v$.confirmRecipientIdentity.$invalid, invalid: v$.confirmRecipientIdentity.$invalid }"
              v-model="v$.confirmRecipientIdentity.$model"
            />
          </div>
          <div class="form-group">
            <label for="third-party-integration-categoryMenus">Category Menus</label>
            <select
              class="form-control"
              id="third-party-integration-categoryMenus"
              data-cy="categoryMenus"
              multiple
              name="categoryMenus"
              v-if="thirdPartyIntegration.categoryMenus !== undefined"
              v-model="thirdPartyIntegration.categoryMenus"
            >
              <option
                v-bind:value="getSelected(thirdPartyIntegration.categoryMenus, menuOption, 'id')"
                v-for="menuOption in menus"
                :key="menuOption.id"
              >
                {{ menuOption.id }}
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
<script lang="ts" src="./third-party-integration-update.component.ts"></script>
